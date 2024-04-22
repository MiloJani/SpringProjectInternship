package com.example.demo.services.impl;

import com.example.demo.entities.Departments;
import com.example.demo.repositories.DepartmentsRepository;
import com.example.demo.services.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImplementation implements DepartmentService {

    public DepartmentsRepository departmentsRepository;

    public DepartmentServiceImplementation(DepartmentsRepository departmentsRepository) {
        this.departmentsRepository = departmentsRepository;
    }

    @Override
    public List<Departments> getAllDepartments() {

        return (List<Departments>) departmentsRepository.findAll();
    }

    @Override
    public Integer addDepartment(Departments department) {

        Departments newDepartment = new Departments();
        newDepartment.setDepartment_id(department.getDepartment_id());
        newDepartment.setDepartment_name(department.getDepartment_name());
        departmentsRepository.save(newDepartment);
        return newDepartment.getDepartment_id();
    }

    @Override
    public Departments updateDepartment(Departments department, Integer id) {

        Optional<Departments> departments = departmentsRepository.findById(id);
        if (departments.isPresent()){

            Departments updateDepartment = departments.get();
            updateDepartment.setDepartment_name(department.getDepartment_name());
            departmentsRepository.save(updateDepartment);
            return updateDepartment;
        }
        return null;
    }

    @Override
    public Integer deleteDepartment(Integer id) {

        Optional<Departments> departments = departmentsRepository.findById(id);
        if (departments.isPresent()){
            Departments deleteDepartment = departments.get();
            departmentsRepository.delete(deleteDepartment);
            return id;
        }
        return null;
    }
}
