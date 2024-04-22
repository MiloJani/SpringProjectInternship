package com.example.demo.rest;

import com.example.demo.entities.Employees;
import com.example.demo.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    ResponseEntity<List<Employees>> getAllEmployees(){

        List<Employees> employees = employeeService.getAllEmployees();

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<String> createEmployee(@RequestBody Employees employee){

        Integer id = employeeService.createEmployee(employee);

        return ResponseEntity.ok("Employee me id: "+id+" u krijua");
    }

    @PutMapping("/{id}")
    ResponseEntity<Employees> updateEmployee(@RequestBody Employees employee,@PathVariable Integer id){

        Employees updatedEmployee = employeeService.updateEmployee(employee,id);

        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteEmployee(@PathVariable Integer id){

        Integer employeeId = employeeService.deleteEmployee(id);

        return ResponseEntity.ok("Employee me id: "+ id +" u fshi");
    }
}
