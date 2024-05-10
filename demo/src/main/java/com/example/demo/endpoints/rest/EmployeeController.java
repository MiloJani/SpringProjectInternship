package com.example.demo.endpoints.rest;

import com.example.demo.core.exceptions.InvalidDataException;
import com.example.demo.core.exceptions.RecordAlreadyExistsException;
import com.example.demo.core.exceptions.RecordNotFoundException;
import com.example.demo.dataproviders.dto.request.ProjectDTO;
import com.example.demo.dataproviders.entities.Employees;
import com.example.demo.dataproviders.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    ResponseEntity<List<Employees>> getAllEmployees(){

        List<Employees> employees = employeeService.getAllEmployees();

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Employees> getEmployeeById(@PathVariable Integer id) throws InvalidDataException, RecordNotFoundException {

        Employees employee = employeeService.getEmployeeById(id);

        return ResponseEntity.ok(employee);
    }

    @GetMapping("/projects/{id}")
    ResponseEntity<List<ProjectDTO>> getAllEmployeesProject(@PathVariable Integer id) throws InvalidDataException,RecordNotFoundException{

        List<ProjectDTO> projects = employeeService.getAllEmployeeProjects(id);

        return new ResponseEntity<>(projects,HttpStatus.OK);

    }
    @PostMapping
    ResponseEntity<String> createEmployee(@RequestBody Employees employee) throws RecordAlreadyExistsException {

        Integer id = employeeService.createEmployee(employee);

        return ResponseEntity.ok("Employee me id: "+id+" u krijua");
    }

    @PutMapping("/{id}")
    ResponseEntity<Employees> updateEmployee(@RequestBody Employees employee,@PathVariable Integer id) throws InvalidDataException,RecordNotFoundException{

        Employees updatedEmployee = employeeService.updateEmployee(employee,id);

        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteEmployee(@PathVariable Integer id) throws InvalidDataException,RecordNotFoundException{

        Integer employeeId = employeeService.deleteEmployee(id);

        return ResponseEntity.ok("Employee me id: "+ employeeId +" u fshi");
    }
}
