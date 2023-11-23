package com.mthor.jwtsecurity.application.util;

import com.mthor.jwtsecurity.domain.entities.Role;
import com.mthor.jwtsecurity.domain.enums.RoleName;
import com.mthor.jwtsecurity.domain.services.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RoleServiceImpl roleService;

    @Override
    public void run(String... args) throws Exception {
        /*
        Role roleAdmin = new Role(RoleName.ADMIN_ROLE);
        Role roleUser = new Role(RoleName.USER_ROLE);
        roleService.saveRole(roleAdmin);
        roleService.saveRole(roleUser);
         */
        System.out.println("Execute create roles");
    }
}
