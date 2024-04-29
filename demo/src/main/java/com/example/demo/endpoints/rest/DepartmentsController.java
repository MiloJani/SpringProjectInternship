package com.example.demo.endpoints.rest;

import com.example.demo.core.exceptions.ErrorResponse;
import com.example.demo.core.exceptions.RecordAlreadyExists;
import com.example.demo.dataproviders.dto.EmployeeDTO;
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

    @GetMapping
    ResponseEntity<List<Departments>> getAllDepartments(){

        List<Departments> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<List<EmployeeDTO>> getAllEmployeesFromDepartment(@PathVariable Integer id){

        List<EmployeeDTO> employees = departmentService.getAllEmployeesFromDepartment(id);

        return new ResponseEntity<>(employees,HttpStatus.OK);
    }

    @GetMapping("/salary/{id}")
    ResponseEntity<String> getTotalEmployeeSalary(@PathVariable Integer id){

        Integer sum = departmentService.getTotalEmployeeSalary(id);

        return ResponseEntity.ok("Total salary e departamentit me id: "+id+" eshte "+sum);
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


//    @ExceptionHandler(value
//            = RecordAlreadyExists.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ErrorResponse
//    handleCustomerAlreadyExistsException(
//            RecordAlreadyExists ex)
//    {
//        return new ErrorResponse(HttpStatus.CONFLICT.value(),
//                ex.getMessage());
//    }

}
