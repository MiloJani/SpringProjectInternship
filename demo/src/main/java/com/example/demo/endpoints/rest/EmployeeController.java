package com.example.demo.endpoints.rest;

import com.example.demo.core.exceptions.InvalidDataException;
import com.example.demo.core.exceptions.RecordAlreadyExistsException;
import com.example.demo.core.exceptions.RecordNotFoundException;
import com.example.demo.dataproviders.dto.request.EmployeeDTO;
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
    ResponseEntity<List<EmployeeDTO>> getAllEmployees(){

        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Integer id) throws InvalidDataException, RecordNotFoundException {

        EmployeeDTO employee = employeeService.getEmployeeById(id);

        return ResponseEntity.ok(employee);
    }

    @GetMapping("/projects/{id}")
    ResponseEntity<List<ProjectDTO>> getAllEmployeesProject(@PathVariable Integer id) throws InvalidDataException,RecordNotFoundException{

        List<ProjectDTO> projects = employeeService.getAllEmployeeProjects(id);

        return new ResponseEntity<>(projects,HttpStatus.OK);

    }
    @PostMapping
    ResponseEntity<String> createEmployee(@RequestBody Employees employee) throws RecordAlreadyExistsException {

        EmployeeDTO employeeDTO = employeeService.createEmployee(employee);

        return ResponseEntity.ok("Employee: "+employeeDTO+" u krijua");
    }

    @PutMapping("/{id}")
    ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody Employees employee,@PathVariable Integer id) throws InvalidDataException,RecordNotFoundException{

        EmployeeDTO updatedEmployee = employeeService.updateEmployee(employee,id);

        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteEmployee(@PathVariable Integer id) throws InvalidDataException,RecordNotFoundException{

        Integer employeeId = employeeService.deleteEmployee(id);

        return ResponseEntity.ok("Employee me id: "+ employeeId +" u fshi");
    }
}
