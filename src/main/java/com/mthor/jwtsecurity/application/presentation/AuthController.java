package com.mthor.jwtsecurity.application.presentation;

import com.mthor.jwtsecurity.domain.dto.JwtDTO;
import com.mthor.jwtsecurity.domain.dto.LoginUserDTO;
import com.mthor.jwtsecurity.domain.dto.Message;
import com.mthor.jwtsecurity.domain.dto.NewUserDTO;
import com.mthor.jwtsecurity.domain.entities.Role;
import com.mthor.jwtsecurity.domain.entities.User;
import com.mthor.jwtsecurity.domain.enums.RoleName;
import com.mthor.jwtsecurity.domain.services.impl.RoleServiceImpl;
import com.mthor.jwtsecurity.domain.services.impl.UserServiceImpl;
import com.mthor.jwtsecurity.infrastructure.security.jwt.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin
@RequestMapping("/auth")
@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    RoleServiceImpl roleService;

    @PostMapping("/signup")
    public ResponseEntity<?> newUser(@Valid @RequestBody NewUserDTO newUserDTO, BindingResult bindingResult){
        logger.error(bindingResult.toString());
        if(bindingResult.hasErrors())
            return new ResponseEntity<>(new Message("¡Incorrect data! Check data."), HttpStatus.BAD_REQUEST);
        if(userService.existByUserName(newUserDTO.getUserName()))
            return new ResponseEntity<>(new Message("User already exists."), HttpStatus.BAD_REQUEST);
        if(userService.existByEmail(newUserDTO.getEmail()))
            return new ResponseEntity<>(new Message("Email already exists."), HttpStatus.BAD_REQUEST);

        User user = new User();
        user.setName(newUserDTO.getName());
        user.setUserName(newUserDTO.getUserName());
        user.setEmail(newUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleService.getByRoleName(RoleName.USER_ROLE).get());
        if(newUserDTO.getRoleSet().contains("admin"))
            roleSet.add(roleService.getByRoleName(RoleName.ADMIN_ROLE).get());
        user.setRoleSet(roleSet);
        userService.saveUser(user);

        return new ResponseEntity<>("User successfully created.", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginUserDTO loginUserDTO, BindingResult bindingResult){
        if(!userService.existByUserName(loginUserDTO.getUserName()))
            return new ResponseEntity<>(new Message("Username don't exists."), HttpStatus.BAD_REQUEST);
        if(bindingResult.hasErrors())
            return new ResponseEntity<>(new Message("Wrong fields"), HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginUserDTO.getUserName(), loginUserDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer ".concat(jwt));

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
