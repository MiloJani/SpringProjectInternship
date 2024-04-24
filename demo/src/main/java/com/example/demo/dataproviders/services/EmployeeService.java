package com.example.demo.dataproviders.services;

import com.example.demo.dataproviders.entities.Employees;
import com.example.demo.dataproviders.dto.ProjectDTO;

import java.util.List;

public interface EmployeeService {

    List<Employees> getAllEmployees();

    Employees getEmployeeById(Integer id);

    List<ProjectDTO> getAllEmployeeProjects(Integer id);

    Integer createEmployee(Employees employee);

    Employees updateEmployee(Employees employees,Integer id);

    Integer deleteEmployee(Integer id);
}
