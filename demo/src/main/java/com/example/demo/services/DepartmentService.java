package com.example.demo.services;

import com.example.demo.entities.Departments;

import java.util.List;

public interface DepartmentService {

    List<Departments> getAllDepartments();

    Integer addDepartment(Departments department);

    Departments updateDepartment(Departments department, Integer id);

    Integer deleteDepartment(Integer id);
}
