package com.mthor.jwtsecurity.domain.services.impl;

import com.mthor.jwtsecurity.domain.entities.User;
import com.mthor.jwtsecurity.domain.services.IUserService;
import com.mthor.jwtsecurity.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userDAO;

    @Override
    public Optional<User> getByUserName(String userName) {
        return userDAO.findByUserName(userName);
    }

    @Override
    public boolean existByUserName(String userName) {
        return userDAO.existsByUserName(userName);
    }

    @Override
    public boolean existByEmail(String email) {
        return userDAO.existsByEmail(email);
    }

    @Override
    public void saveUser(User user){
        userDAO.save(user);
    }
}
