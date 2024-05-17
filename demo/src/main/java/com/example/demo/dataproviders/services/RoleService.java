package com.example.demo.dataproviders.services;

import com.example.demo.dataproviders.dto.request.RoleDTO;
import com.example.demo.dataproviders.entities.Role;


public interface RoleService {

    Integer createRole(RoleDTO role);
}
