package com.example.demo.endpoints.rest;

import com.example.demo.core.exceptions.InvalidDataException;
import com.example.demo.core.exceptions.RecordAlreadyExistsException;
import com.example.demo.core.exceptions.RecordNotFoundException;
import com.example.demo.dataproviders.dto.request.RoleDTO;
import com.example.demo.dataproviders.entities.Departments;
import com.example.demo.dataproviders.entities.Role;
import com.example.demo.dataproviders.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    ResponseEntity<List<Role>> getAllRoles(){

        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles,HttpStatus.OK );
    }

    @PostMapping
    ResponseEntity<String> createRole(@RequestBody RoleDTO roleDTO) throws RecordAlreadyExistsException {
        Integer id= roleService.createRole(roleDTO);
        return ResponseEntity.ok("Roli me id:"+id+" u krijua");
    }

    @PutMapping("/{id}")
    ResponseEntity<Role> updateRole(@RequestBody Role role,@PathVariable Integer id) throws InvalidDataException, RecordNotFoundException {
        Role role1 = roleService.updateRole(role,id);
        return ResponseEntity.ok(role1);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteRole(@PathVariable Integer id) throws InvalidDataException,RecordNotFoundException{

        Integer roleId=roleService.deleteRole(id);
        return ResponseEntity.ok("Departamenti me id:"+roleId+" u fshi");

    }

}
