package com.example.demo.repository;

import com.example.demo.dataproviders.entities.Permissions;
import com.example.demo.dataproviders.entities.Role;
import com.example.demo.dataproviders.repositories.PermissionRepository;
import com.example.demo.dataproviders.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class PermissionRepositoryTest {

    private PermissionRepository permissionRepository;
    private RoleRepository roleRepository;

    @Autowired
    public PermissionRepositoryTest(PermissionRepository permissionRepository, RoleRepository roleRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
    }

    @Test
    public void permissionRepository_savePermission_ReturnSavedPermission() {
        Role role = Role.builder()
                .roleId(1)
                .roleName("USER")
                .permissions(Collections.emptyList())
                .build();
        roleRepository.save(role);

        Permissions permission = Permissions.builder()
                .permissionId(1)
                .permissionName("USER_READ")
                .roles(Collections.singletonList(role))
                .build();

        Permissions savedPermission = permissionRepository.save(permission);

        assertThat(savedPermission).isNotNull();
        assertThat(savedPermission.getPermissionId()).isNotNull();
        assertThat(savedPermission.getPermissionName()).isEqualTo("USER_READ");
    }

    @Test
    public void permissionRepository_findPermissionByPermissionName_ReturnPermission() {
        Permissions permission = Permissions.builder()
                .permissionId(1)
                .permissionName("ADMIN_READ")
                .roles(Collections.emptyList())
                .build();
        permissionRepository.save(permission);

        Optional<Permissions> foundPermission = permissionRepository.findPermissionByPermissionName("ADMIN_READ");

        assertThat(foundPermission).isPresent();
        assertThat(foundPermission.get().getPermissionName()).isEqualTo("ADMIN_READ");
    }
}
