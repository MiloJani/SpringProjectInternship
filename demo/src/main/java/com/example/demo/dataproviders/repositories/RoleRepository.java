package com.example.demo.dataproviders.repositories;

import com.example.demo.dataproviders.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findRoleByRoleName(String roleName);
}
