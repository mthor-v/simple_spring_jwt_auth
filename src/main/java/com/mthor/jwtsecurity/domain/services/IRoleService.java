package com.mthor.jwtsecurity.domain.services;

import com.mthor.jwtsecurity.domain.entities.Role;
import com.mthor.jwtsecurity.domain.enums.RoleName;

import java.util.Optional;

public interface IRoleService {

    public Optional<Role> getByRoleName(RoleName roleName);
    public void saveRole(Role role);
}
