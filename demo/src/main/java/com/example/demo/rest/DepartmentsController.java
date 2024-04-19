package com.example.demo.rest;

import com.example.demo.entities.Departments;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentsController {

    @PostMapping("/departments")
    ResponseEntity<String> addDepartment(@Valid @RequestBody Departments department) {
        // persisting the user
        return ResponseEntity.ok("Department is valid");
    }
}
