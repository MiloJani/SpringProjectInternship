package com.example.demo.dataproviders.repositories;

import com.example.demo.dataproviders.entities.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permissions, Integer> {
    Optional<Permissions> findPermissionByPermissionName(String permissionName);
}
