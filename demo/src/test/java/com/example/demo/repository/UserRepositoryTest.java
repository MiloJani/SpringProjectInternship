package com.example.demo.repository;

import com.example.demo.dataproviders.entities.Permissions;
import com.example.demo.dataproviders.entities.Role;
import com.example.demo.dataproviders.entities.User;
import com.example.demo.dataproviders.repositories.PermissionRepository;
import com.example.demo.dataproviders.repositories.RoleRepository;
import com.example.demo.dataproviders.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Test
    public void userRepository_saveUser_ReturnSavedUser() {
        Permissions permission = Permissions.builder()
                .permissionId(1)
                .permissionName("USER_READ")
                .roles(new ArrayList<>())
                .build();
        permissionRepository.save(permission);


        Role role = Role.builder()
                .roleId(1).roleName("USER")
                .permissions(Collections.singletonList(permission))
                .build();
        roleRepository.save(role);

        permission.setRoles(Collections.singletonList(role));
        permissionRepository.save(permission);

        User user = User.builder()
                .firstname("Milo")
                .lastname("Molla")
                .email("emiljano.molla2@gmail.com")
                .password("password")
                .roles(Collections.singletonList(role))
                .build();

        User savedUser = userRepository.save(user);

        role.setUsers(Collections.singletonList(user));
        roleRepository.save(role);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUserId()).isNotNull();
        assertThat(savedUser.getAuthorities().iterator().next().getAuthority()).isEqualTo("USER_READ");
    }

    @Test
    public void userRepository_findUserByEmail_ReturnUser() {
        User user = User.builder()
                .firstname("Milo")
                .lastname("Molla")
                .email("emiljano.molla2@gmail.com")
                .password("password")
                .roles(Collections.emptyList())
                .build();

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("emiljano.molla2@gmail.com");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("emiljano.molla2@gmail.com");
    }



}
