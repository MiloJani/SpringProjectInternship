package com.example.demo.repository;

import com.example.demo.dataproviders.entities.Role;
import com.example.demo.dataproviders.entities.Permissions;
import com.example.demo.dataproviders.repositories.PermissionRepository;
import com.example.demo.dataproviders.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class RoleRepositoryTest {

    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    @Autowired
    public RoleRepositoryTest(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Test
    public void roleRepository_saveRole_ReturnSavedRole() {
        Permissions permission = Permissions.builder()
                .permissionId(1)
                .permissionName("USER_READ")
                .roles(Collections.emptyList())
                .build();
        permissionRepository.save(permission);

        Role role = Role.builder()
                .roleId(1)
                .roleName("USER")
                .permissions(Collections.singletonList(permission))
                .build();

        Role savedRole = roleRepository.save(role);

        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getRoleId()).isNotNull();
        assertThat(savedRole.getPermissions().iterator().next().getPermissionName()).isEqualTo("USER_READ");
    }

    @Test
    public void roleRepository_findRoleByRoleName_ReturnRole() {
        Role role = Role.builder()
                .roleId(1)
                .roleName("ADMIN")
                .permissions(Collections.emptyList())
                .build();
        roleRepository.save(role);

        Optional<Role> foundRole = roleRepository.findRoleByRoleName("ADMIN");

        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getRoleName()).isEqualTo("ADMIN");
    }
}
