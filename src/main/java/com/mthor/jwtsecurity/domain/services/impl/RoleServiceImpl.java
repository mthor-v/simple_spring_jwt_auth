package com.mthor.jwtsecurity.domain.services.impl;

import com.mthor.jwtsecurity.domain.entities.Role;
import com.mthor.jwtsecurity.domain.enums.RoleName;
import com.mthor.jwtsecurity.domain.services.IRoleService;
import com.mthor.jwtsecurity.infrastructure.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleDAO;

    @Override
    public Optional<Role> getByRoleName(RoleName roleName) {
        return roleDAO.findByRoleName(roleName);
    }

    @Override
    public void saveRole(Role role) {
        roleDAO.save(role);
    }

}
