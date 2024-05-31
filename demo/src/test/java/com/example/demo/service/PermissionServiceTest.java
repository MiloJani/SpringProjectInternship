package com.example.demo.service;

import com.example.demo.core.exceptions.InvalidDataException;
import com.example.demo.core.exceptions.RecordAlreadyExistsException;
import com.example.demo.core.exceptions.RecordNotFoundException;
import com.example.demo.dataproviders.dto.request.PermissionDTO;
import com.example.demo.dataproviders.entities.Permissions;
import com.example.demo.dataproviders.repositories.PermissionRepository;
import com.example.demo.dataproviders.services.impl.PermissionsServiceImplementation;
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
public class PermissionServiceTest {

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private PermissionsServiceImplementation permissionService;

    private Permissions permission;
    private PermissionDTO permissionDTO;

    @BeforeEach
    public void init() {
        permission = Permissions.builder()
                .permissionId(1)
                .permissionName("READ")
                .build();

        permissionDTO = PermissionDTO.builder()
                .permissionId(1)
                .permissionName("READ")
                .build();
    }

    @Test
    public void permissionService_getAllPermissions_returnPermissionList() {
        when(permissionRepository.findAll()).thenReturn(Collections.singletonList(permission));

        Assertions.assertThat(permissionService.getAllPermissions()).hasSize(1).contains(permission);
    }

    @Test
    public void permissionService_createPermission_ReturnPermissionId() throws RecordAlreadyExistsException {
        when(permissionRepository.findPermissionByPermissionName(anyString())).thenReturn(Optional.empty());
        when(permissionRepository.save(Mockito.any(Permissions.class))).thenReturn(permission);

        PermissionDTO createdPermission = permissionService.createPermission(permission);

        Assertions.assertThat(createdPermission).isNotNull();
        Assertions.assertThat(createdPermission.getPermissionId()).isEqualTo(permission.getPermissionId());
    }

    @Test
    public void permissionService_createPermission_ThrowsRecordAlreadyExistsException() {
        when(permissionRepository.findPermissionByPermissionName(anyString())).thenReturn(Optional.of(permission));

        Assertions.assertThatThrownBy(() -> permissionService.createPermission(permission))
                .isInstanceOf(RecordAlreadyExistsException.class)
                .hasMessageContaining("Permission exists");
    }

    @Test
    public void permissionService_updatePermission_ReturnUpdatedPermission() throws RecordNotFoundException, InvalidDataException {
        when(permissionRepository.findById(anyInt())).thenReturn(Optional.of(permission));
        when(permissionRepository.save(Mockito.any(Permissions.class))).thenReturn(permission);

        Permissions updatedPermission = permissionService.updatePermission(permission, permission.getPermissionId());

        Assertions.assertThat(updatedPermission).isNotNull();
        Assertions.assertThat(updatedPermission.getPermissionId()).isEqualTo(permission.getPermissionId());
    }

    @Test
    public void permissionService_updatePermission_ThrowsInvalidDataException() {
        Assertions.assertThatThrownBy(() -> permissionService.updatePermission(permission, -1))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Id value is not acceptable");
    }

    @Test
    public void permissionService_updatePermission_ThrowsRecordNotFoundException() {
        when(permissionRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> permissionService.updatePermission(permission, permission.getPermissionId()))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessageContaining("Nuk u gjet permission me kete id");
    }

    @Test
    public void permissionService_deletePermission_ReturnDeletedPermissionId() throws RecordNotFoundException, InvalidDataException {
        when(permissionRepository.findById(anyInt())).thenReturn(Optional.of(permission));
        doNothing().when(permissionRepository).delete(any(Permissions.class));

        Integer deletedPermissionId = permissionService.deletePermission(permission.getPermissionId());

        Assertions.assertThat(deletedPermissionId).isNotNull();
        Assertions.assertThat(deletedPermissionId).isEqualTo(permission.getPermissionId());
    }

    @Test
    public void permissionService_deletePermission_ThrowsInvalidDataException() {
        Assertions.assertThatThrownBy(() -> permissionService.deletePermission(-1))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Id value is not acceptable");
    }

    @Test
    public void permissionService_deletePermission_ThrowsRecordNotFoundException() {
        when(permissionRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> permissionService.deletePermission(permission.getPermissionId()))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessageContaining("Nuk u gjet permission me kete id");
    }
}
