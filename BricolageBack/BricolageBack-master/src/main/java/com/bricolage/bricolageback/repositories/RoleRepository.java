package com.bricolage.bricolageback.repositories;

import com.bricolage.bricolageback.config.security.Roles;
import com.bricolage.bricolageback.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(Roles roleName);
}
