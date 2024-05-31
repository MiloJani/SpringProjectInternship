package com.example.demo.dataproviders.services.impl;

import com.example.demo.core.exceptions.InvalidDataException;
import com.example.demo.core.exceptions.RecordAlreadyExistsException;
import com.example.demo.core.exceptions.RecordNotFoundException;
import com.example.demo.dataproviders.dto.request.PermissionDTO;
import com.example.demo.dataproviders.entities.Permissions;
import com.example.demo.dataproviders.entities.Role;
import com.example.demo.dataproviders.repositories.PermissionRepository;
import com.example.demo.dataproviders.services.PermissionsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionsServiceImplementation implements PermissionsService {

    private final PermissionRepository permissionRepository;

    public PermissionsServiceImplementation(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<Permissions> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public PermissionDTO createPermission(Permissions permissions) throws RecordAlreadyExistsException {
        if (permissionRepository.findPermissionByPermissionName
                (permissions.getPermissionName()).isPresent()){
            throw new RecordAlreadyExistsException("Permission exists");
        }
        Permissions savedPermission = permissionRepository.save(permissions);

        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setPermissionId(savedPermission.getPermissionId());
        permissionDTO.setPermissionName(savedPermission.getPermissionName());

        return permissionDTO;
    }

    @Override
    public Permissions updatePermission(Permissions permissions, Integer id) throws InvalidDataException,RecordNotFoundException {
        if (id<=0) {
            throw new InvalidDataException("Id value is not acceptable");
        }
        Optional<Permissions> permission = permissionRepository.findById(id);
        if (permission.isPresent()){

            Permissions updatePermission= permission.get();
            updatePermission.setPermissionName(permissions.getPermissionName());
            permissionRepository.save(updatePermission);
            return updatePermission;
        }
        else throw new RecordNotFoundException(
                "Nuk u gjet permission me kete id");
    }

    @Override
    public Integer deletePermission(Integer id) throws InvalidDataException,RecordNotFoundException {
        if (id<=0) {
            throw new InvalidDataException("Id value is not acceptable");
        }
        Optional<Permissions> permission = permissionRepository.findById(id);
        if (permission.isPresent()){
            Permissions deletePermission = permission.get();
            permissionRepository.delete(deletePermission);
            return id;
        }
        else throw new RecordNotFoundException(
                "Nuk u gjet permission me kete id");
    }
}
