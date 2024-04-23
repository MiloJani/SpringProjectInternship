package com.example.demo.services;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.entities.Departments;
import com.example.demo.entities.Employees;

import java.util.List;

public interface DepartmentService {

    List<Departments> getAllDepartments();

    List<EmployeeDTO> getAllEmployeesFromDepartment(Integer id);

    Integer getTotalEmployeeSalary(Integer id);

    Integer addDepartment(Departments department);

    Departments updateDepartment(Departments department, Integer id);

    Integer deleteDepartment(Integer id);


}
