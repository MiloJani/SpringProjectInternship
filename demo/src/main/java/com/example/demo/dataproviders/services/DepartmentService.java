package com.example.demo.dataproviders.services;

import com.example.demo.dataproviders.dto.request.EmployeeDTO;
import com.example.demo.dataproviders.entities.Departments;

import java.util.List;

public interface DepartmentService {

    List<Departments> getAllDepartments();

    List<EmployeeDTO> getAllEmployeesFromDepartment(Integer id);

    Integer getTotalEmployeeSalary(Integer id);

    Integer addDepartment(Departments department);

    Departments updateDepartment(Departments department, Integer id);

    Integer deleteDepartment(Integer id);


}
