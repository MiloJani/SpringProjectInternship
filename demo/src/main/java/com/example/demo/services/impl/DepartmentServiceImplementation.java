package com.example.demo.services.impl;

import com.example.demo.entities.Departments;
import com.example.demo.repositories.DepartmentsRepository;
import com.example.demo.services.DepartmentService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImplementation implements DepartmentService {

    public DepartmentsRepository departmentsRepository;

    public DepartmentServiceImplementation(DepartmentsRepository departmentsRepository) {
        this.departmentsRepository = departmentsRepository;
    }

    @Override
    public Integer addDepartment(Departments department) {

        Departments newDepartment = new Departments();
        newDepartment.setDepartment_id(department.getDepartment_id());
        newDepartment.setDepartment_name(department.getDepartment_name());
        departmentsRepository.save(newDepartment);
        return newDepartment.getDepartment_id();
    }
}
