package com.example.demo.services;

import com.example.demo.entities.Employees;

import java.util.List;

public interface EmployeeService {

    List<Employees> getAllEmployees();

    Integer createEmployee(Employees employee);

    Employees updateEmployee(Employees employees,Integer id);

    Integer deleteEmployee(Integer id);
}
