package com.example.demo.dataproviders.services.impl;

import com.example.demo.dataproviders.dto.request.RoleDTO;
import com.example.demo.dataproviders.entities.Role;
import com.example.demo.dataproviders.repositories.RoleRepository;
import com.example.demo.dataproviders.services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImplementation implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImplementation(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Integer createRole(RoleDTO role) {
        Role role1 = new Role();
        role1.setRoleName(role.getRoleName());
        roleRepository.save(role1);
        return role1.getRoleId();
    }
}
