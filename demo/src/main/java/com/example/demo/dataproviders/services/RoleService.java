package com.example.demo.dataproviders.services;

import com.example.demo.dataproviders.dto.request.RoleDTO;
import com.example.demo.dataproviders.entities.Role;

import java.util.List;


public interface RoleService {

    List<Role> getAllRoles();
    RoleDTO createRole(Role role);

    Role updateRole(Role role, Integer id);

    Integer deleteRole(Integer id);
}
