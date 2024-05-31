package com.example.demo.endpoints.rest;

import com.example.demo.core.exceptions.InvalidDataException;
import com.example.demo.core.exceptions.RecordAlreadyExistsException;
import com.example.demo.core.exceptions.RecordNotFoundException;
import com.example.demo.dataproviders.dto.request.PermissionDTO;
import com.example.demo.dataproviders.entities.Permissions;
import com.example.demo.dataproviders.services.PermissionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionsController {

    private final PermissionsService permissionsService;

    public PermissionsController(PermissionsService permissionsService) {
        this.permissionsService = permissionsService;
    }

    @GetMapping
    ResponseEntity<List<Permissions>> getAllPermissions(){

        List<Permissions> permissions = permissionsService.getAllPermissions();
        return new ResponseEntity<>(permissions,HttpStatus.OK );
    }

    @PostMapping("/create")
    ResponseEntity<String> createPermission(@RequestBody Permissions permissions) throws RecordAlreadyExistsException {
        PermissionDTO permissionDTO= permissionsService.createPermission(permissions);
        return ResponseEntity.ok("Permission me id:"+permissionDTO.getPermissionId()+" u krijua");
    }

    @PutMapping("/{id}")
    ResponseEntity<Permissions> updatePermission(@RequestBody Permissions permissions,@PathVariable Integer id) throws InvalidDataException, RecordNotFoundException {
        Permissions permission = permissionsService.updatePermission(permissions,id);
        return ResponseEntity.ok(permission);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deletePermission(@PathVariable Integer id) throws InvalidDataException,RecordNotFoundException{

        Integer permissionId=permissionsService.deletePermission(id);
        return ResponseEntity.ok("Permission me id:"+permissionId+" u fshi");

    }

}
