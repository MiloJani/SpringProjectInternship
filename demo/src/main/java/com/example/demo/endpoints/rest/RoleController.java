package com.example.demo.endpoints.rest;

import com.example.demo.dataproviders.dto.request.RoleDTO;
import com.example.demo.dataproviders.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    ResponseEntity<String> createRole(@RequestBody RoleDTO roleDTO){
        Integer id= roleService.createRole(roleDTO);
        return ResponseEntity.ok("Roli me id:"+id+" u krijua");
    }
}
