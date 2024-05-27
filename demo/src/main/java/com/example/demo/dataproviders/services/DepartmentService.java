package com.example.demo.dataproviders.services;

import com.example.demo.dataproviders.dto.request.DepartmentDTO;
import com.example.demo.dataproviders.dto.request.EmployeeDTO;
import com.example.demo.dataproviders.entities.Departments;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDTO> getAllDepartments();

    DepartmentDTO getDepartmentById(Integer id);

    List<EmployeeDTO> getAllEmployeesFromDepartment(Integer id);

    Integer getTotalEmployeeSalary(Integer id);

    DepartmentDTO addDepartment(Departments department);

    DepartmentDTO updateDepartment(Departments department, Integer id);

    Integer deleteDepartment(Integer id);


}
