package com.mthor.jwtsecurity.domain.services;

import com.mthor.jwtsecurity.domain.entities.User;

import java.util.Optional;

public interface IUserService {

    public Optional<User> getByUserName(String userName);

    public boolean existByUserName(String userName);

    public boolean existByEmail(String email);

    public void saveUser(User user);
}
