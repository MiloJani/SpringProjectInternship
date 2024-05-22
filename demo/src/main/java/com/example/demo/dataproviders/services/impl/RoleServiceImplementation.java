package com.example.demo.dataproviders.services.impl;

import com.example.demo.core.exceptions.InvalidDataException;
import com.example.demo.core.exceptions.RecordAlreadyExistsException;
import com.example.demo.core.exceptions.RecordNotFoundException;
import com.example.demo.dataproviders.dto.request.RoleDTO;
import com.example.demo.dataproviders.entities.Departments;
import com.example.demo.dataproviders.entities.Role;
import com.example.demo.dataproviders.repositories.RoleRepository;
import com.example.demo.dataproviders.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImplementation implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImplementation(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Integer createRole(RoleDTO role) throws RecordAlreadyExistsException {
        if (roleRepository.findRoleByRoleName(role.getRoleName()).isPresent()){
            throw new RecordAlreadyExistsException("Role exists");
        }
        Role role1 = new Role();
        role1.setRoleName(role.getRoleName());
        roleRepository.save(role1);
        return role1.getRoleId();
    }

    @Override
    public Role updateRole(Role role, Integer id) throws InvalidDataException ,RecordNotFoundException{

        if (id<=0) {
            throw new InvalidDataException("Id value is not acceptable");
        }
        Optional<Role> role1 = roleRepository.findById(id);
        if (role1.isPresent()){

            Role updateRole= role1.get();
            updateRole.setRoleName(role.getRoleName());
            roleRepository.save(updateRole);
            return updateRole;
        }
        else throw new RecordNotFoundException(
                "Nuk u gjet role me kete id");
    }

    @Override
    public Integer deleteRole(Integer id) throws InvalidDataException,RecordNotFoundException {
        if (id<=0) {
            throw new InvalidDataException("Id value is not acceptable");
        }
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()){
            Role deleteRole = role.get();
            roleRepository.delete(deleteRole);
            return id;
        }
        else throw new RecordNotFoundException(
                "Nuk u gjet role me kete id");
    }

}
