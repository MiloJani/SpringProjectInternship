package com.example.demo.rest;

import com.example.demo.entities.Departments;
import com.example.demo.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/departments")
public class DepartmentsController {

    private DepartmentService departmentService;

    public DepartmentsController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    ResponseEntity<String> addDepartment(@Valid @RequestBody Departments department) {
        // persisting the user
        Integer id = departmentService.addDepartment(department);
        return ResponseEntity.ok("Department is valid:"+id);
    }
}
