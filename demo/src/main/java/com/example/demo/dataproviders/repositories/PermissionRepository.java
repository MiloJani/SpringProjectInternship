package com.example.demo.dataproviders.repositories;

import com.example.demo.dataproviders.entities.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permissions, Integer> {
    Optional<Permissions> findPermissionByPermissionName(String permissionName);
}
