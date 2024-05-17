package com.example.demo.dataproviders.repositories;

import com.example.demo.dataproviders.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findRoleByRoleName(String roleName);
}
