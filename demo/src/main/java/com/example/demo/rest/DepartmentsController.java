package com.example.demo.rest;

import com.example.demo.entities.Departments;
import com.example.demo.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentsController {

    private DepartmentService departmentService;

    public DepartmentsController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    ResponseEntity<List<Departments>> getAllDepartments(){

        List<Departments> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }
    @PostMapping
    ResponseEntity<String> addDepartment(@Valid @RequestBody Departments department) {

        Integer id = departmentService.addDepartment(department);
        return ResponseEntity.ok("Departmenti me id:"+id+" u krijua");
    }

    @PutMapping("/{id}")
    ResponseEntity<Departments> updateDepartment(@RequestBody Departments department,
                                                 @PathVariable Integer id){

        Departments updatedDepartment = departmentService.updateDepartment(department,id);
        return ResponseEntity.ok(updatedDepartment);

    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteDepartment(@PathVariable Integer id){

        Integer departmentId = departmentService.deleteDepartment(id);
        return ResponseEntity.ok("Departamenti me id:"+departmentId+" u fshi");
    }
}
