package com.example.demo.service;

import com.example.demo.core.exceptions.InvalidDataException;
import com.example.demo.core.exceptions.RecordAlreadyExistsException;
import com.example.demo.core.exceptions.RecordNotFoundException;
import com.example.demo.dataproviders.dto.request.RoleDTO;
import com.example.demo.dataproviders.entities.Role;
import com.example.demo.dataproviders.repositories.RoleRepository;
import com.example.demo.dataproviders.services.impl.RoleServiceImplementation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImplementation roleService;

    private Role role;
    private RoleDTO roleDTO;

    @BeforeEach
    public void init() {
        role = Role.builder()
                .roleId(1)
                .roleName("USER")
                .permissions(Collections.emptyList())
                .build();

        roleDTO = RoleDTO.builder()
                .roleId(1)
                .roleName("USER")
                .build();
    }

    @Test
    public void roleService_getAllRoles_returnRoleList() {
        when(roleRepository.findAll()).thenReturn(Collections.singletonList(role));

        Assertions.assertThat(roleService.getAllRoles()).hasSize(1).contains(role);
    }

    @Test
    public void roleService_createRole_ReturnRoleId() throws RecordAlreadyExistsException {
        when(roleRepository.findRoleByRoleName(anyString())).thenReturn(Optional.empty());
        when(roleRepository.save(Mockito.any(Role.class))).thenReturn(role);

        RoleDTO createdRole = roleService.createRole(role);

        Assertions.assertThat(createdRole).isNotNull();
        Assertions.assertThat(createdRole.getRoleId()).isEqualTo(role.getRoleId());
    }

    @Test
    public void roleService_createRole_ThrowsRecordAlreadyExistsException() {
        when(roleRepository.findRoleByRoleName(anyString())).thenReturn(Optional.of(role));

        Assertions.assertThatThrownBy(() -> roleService.createRole(role))
                .isInstanceOf(RecordAlreadyExistsException.class)
                .hasMessageContaining("Role exists");
    }

    @Test
    public void roleService_updateRole_ReturnUpdatedRole() throws RecordNotFoundException, InvalidDataException {
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));
        when(roleRepository.save(Mockito.any(Role.class))).thenReturn(role);

        Role updatedRole = roleService.updateRole(role, role.getRoleId());

        Assertions.assertThat(updatedRole).isNotNull();
        Assertions.assertThat(updatedRole.getRoleId()).isEqualTo(role.getRoleId());
    }

    @Test
    public void roleService_updateRole_ThrowsInvalidDataException() {
        Assertions.assertThatThrownBy(() -> roleService.updateRole(role, -1))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Id value is not acceptable");
    }

    @Test
    public void roleService_updateRole_ThrowsRecordNotFoundException() {
        when(roleRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> roleService.updateRole(role, role.getRoleId()))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessageContaining("Nuk u gjet role me kete id");
    }

    @Test
    public void roleService_deleteRole_ReturnDeletedRoleId() throws RecordNotFoundException, InvalidDataException {
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));
        doNothing().when(roleRepository).delete(any(Role.class));

        Integer deletedRoleId = roleService.deleteRole(role.getRoleId());

        Assertions.assertThat(deletedRoleId).isNotNull();
        Assertions.assertThat(deletedRoleId).isEqualTo(role.getRoleId());
    }

    @Test
    public void roleService_deleteRole_ThrowsInvalidDataException() {
        Assertions.assertThatThrownBy(() -> roleService.deleteRole(-1))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Id value is not acceptable");
    }

    @Test
    public void roleService_deleteRole_ThrowsRecordNotFoundException() {
        when(roleRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> roleService.deleteRole(role.getRoleId()))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessageContaining("Nuk u gjet role me kete id");
    }
}
