package com.example.demo.dataproviders.services;

import com.example.demo.dataproviders.dto.request.PermissionDTO;
import com.example.demo.dataproviders.entities.Permissions;

import java.util.List;


public interface PermissionsService {

    List<Permissions> getAllPermissions();
    Integer createPermission(PermissionDTO permissionDTO);

    Permissions updatePermission(Permissions permissions, Integer id);

    Integer deletePermission(Integer id);
}
