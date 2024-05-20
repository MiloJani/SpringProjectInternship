package com.example.demo.dataproviders.repositories;

import com.example.demo.dataproviders.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findRoleByRoleName(String roleName);
}
