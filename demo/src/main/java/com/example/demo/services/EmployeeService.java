package com.example.demo.services;

import com.example.demo.dto.ProjectDTO;
import com.example.demo.entities.Employees;

import java.util.List;

public interface EmployeeService {

    List<Employees> getAllEmployees();

    Employees getEmployeeById(Integer id);

    List<ProjectDTO> getAllEmployeeProjects(Integer id);

    Integer createEmployee(Employees employee);

    Employees updateEmployee(Employees employees,Integer id);

    Integer deleteEmployee(Integer id);
}
