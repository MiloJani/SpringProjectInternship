package com.example.demo.endpoints.rest;

import com.example.demo.core.exceptions.InvalidDataException;
import com.example.demo.core.exceptions.RecordAlreadyExistsException;
import com.example.demo.core.exceptions.RecordNotFoundException;
import com.example.demo.dataproviders.dto.request.DepartmentDTO;
import com.example.demo.dataproviders.dto.request.EmployeeDTO;
import com.example.demo.dataproviders.entities.Departments;
import com.example.demo.dataproviders.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentsController {

    private final DepartmentService departmentService;

    public DepartmentsController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/all")
    ResponseEntity<List<DepartmentDTO>> getAllDepartments(){

        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Integer id){

        DepartmentDTO departmentDTO = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(departmentDTO);
    }

    @GetMapping("/{id}")
    ResponseEntity<List<EmployeeDTO>> getAllEmployeesFromDepartment(@PathVariable Integer id) throws InvalidDataException, RecordNotFoundException {

        List<EmployeeDTO> employees = departmentService.getAllEmployeesFromDepartment(id);

        return new ResponseEntity<>(employees,HttpStatus.OK);
    }

    @GetMapping("/salary/{id}")
    ResponseEntity<String> getTotalEmployeeSalary(@PathVariable Integer id) throws InvalidDataException,RecordNotFoundException{

        Integer sum = departmentService.getTotalEmployeeSalary(id);

        return ResponseEntity.ok("Total salary e departamentit me id: "+id+" eshte "+sum);
    }

    @PostMapping("/add")
    ResponseEntity<DepartmentDTO> addDepartment(@Valid @RequestBody Departments department) throws RecordAlreadyExistsException {

        DepartmentDTO dep = departmentService.addDepartment(department);
        return new ResponseEntity<>(dep,HttpStatus.CREATED);
//        return ResponseEntity.ok("Departmenti me id:"+dep.getDepartmentId()+" u krijua");
    }

    @PutMapping("/{id}")
    ResponseEntity<DepartmentDTO> updateDepartment(@RequestBody Departments department,
                                                 @PathVariable Integer id) throws InvalidDataException,RecordNotFoundException{

        DepartmentDTO updatedDepartment = departmentService.updateDepartment(department,id);
        return ResponseEntity.ok(updatedDepartment);

    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteDepartment(@PathVariable Integer id) throws InvalidDataException,RecordNotFoundException{

        Integer departmentId = departmentService.deleteDepartment(id);
        return ResponseEntity.ok("Departamenti me id:"+departmentId+" u fshi");
    }



}
