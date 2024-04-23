package com.example.demo.services.impl;

import com.example.demo.dto.ProjectDTO;
import com.example.demo.entities.Employees;
import com.example.demo.entities.Projects;
import com.example.demo.repositories.EmployeesRepository;
import com.example.demo.services.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Employees getEmployeeById(Integer id) {

        Optional<Employees> employee = employeesRepository.findById(id);

        if (employee.isPresent()){

            Employees foundEmployee = employee.get();

            return foundEmployee;
        }
        return null;
    }

    @Override
    public List<ProjectDTO> getAllEmployeeProjects(Integer id) {

        Optional<Employees> employee = employeesRepository.findById(id);

        List<ProjectDTO> projectDTOS = new ArrayList<>();

        if (employee.isPresent()){

            Employees foundEmployee = employee.get();

            List<Projects> projects = foundEmployee.getProjects();

            for (Projects project : projects){

                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setProject_name(project.getProject_name());
                projectDTO.setStart_date(project.getStart_date());
                projectDTO.setEnd_date(project.getEnd_date());

                projectDTOS.add(projectDTO);
            }


        }
        return projectDTOS;
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
//        newEmployee.setProjects(employee.getProjects());
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
