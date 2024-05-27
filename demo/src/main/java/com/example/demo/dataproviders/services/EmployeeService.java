package com.example.demo.dataproviders.services;

import com.example.demo.dataproviders.dto.request.EmployeeDTO;
import com.example.demo.dataproviders.entities.Employees;
import com.example.demo.dataproviders.dto.request.ProjectDTO;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployeeById(Integer id);

    List<ProjectDTO> getAllEmployeeProjects(Integer id);

    EmployeeDTO createEmployee(Employees employee);

    EmployeeDTO updateEmployee(Employees employees,Integer id);

    Integer deleteEmployee(Integer id);
}
