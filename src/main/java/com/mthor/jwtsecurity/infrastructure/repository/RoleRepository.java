package com.mthor.jwtsecurity.infrastructure.repository;

import com.mthor.jwtsecurity.domain.entities.Role;
import com.mthor.jwtsecurity.domain.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>{

    Optional<Role> findByRoleName(@NotNull RoleName roleName);
}
