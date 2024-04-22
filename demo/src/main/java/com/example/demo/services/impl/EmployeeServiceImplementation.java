package com.example.demo.services.impl;

import com.example.demo.entities.Employees;
import com.example.demo.repositories.EmployeesRepository;
import com.example.demo.services.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

    private EmployeesRepository employeesRepository;

    public EmployeeServiceImplementation(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    @Override
    public List<Employees> getAllEmployees() {

        return (List<Employees>) employeesRepository.findAll() ;
    }

    @Override
    public Integer createEmployee(Employees employee) {

        Employees newEmployee = new Employees();
        newEmployee.setEmployee_id(employee.getEmployee_id());
        newEmployee.setFirst_name(employee.getFirst_name());
        newEmployee.setLast_name(employee.getLast_name());
        newEmployee.setJob_title(employee.getJob_title());
        newEmployee.setSalary(employee.getSalary());
        newEmployee.setDepartment(employee.getDepartment());
        newEmployee.setProjects(employee.getProjects());
        employeesRepository.save(newEmployee);
        return newEmployee.getEmployee_id();
    }

    @Override
    public Employees updateEmployee(Employees employee, Integer id) {

        Optional<Employees> employees = employeesRepository.findById(id);

        if (employees.isPresent()){

            Employees updateEmployee = employees.get();
            updateEmployee.setSalary(employee.getSalary());
            employeesRepository.save(updateEmployee);
            return updateEmployee;
        }
        return null;
    }

    @Override
    public Integer deleteEmployee(Integer id) {

        Optional<Employees> employee = employeesRepository.findById(id);

        if (employee.isPresent()){

            Employees deleteEmployee = employee.get();
            employeesRepository.delete(deleteEmployee);
            return id;
        }
        return null;
    }
}
