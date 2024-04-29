package com.example.demo.dataproviders.services.impl;

import com.example.demo.core.exceptions.RecordAlreadyExists;
import com.example.demo.dataproviders.entities.Employees;
import com.example.demo.dataproviders.repositories.EmployeesRepository;
import com.example.demo.dataproviders.services.EmployeeService;
import com.example.demo.dataproviders.dto.ProjectDTO;
import com.example.demo.dataproviders.entities.Projects;
import com.example.demo.core.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

    private final EmployeesRepository employeesRepository;

    public EmployeeServiceImplementation(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    @Override
    public List<Employees> getAllEmployees() {

        return employeesRepository.findAll();
    }

    @Override
    public Employees getEmployeeById(Integer id) {

        Optional<Employees> employee = employeesRepository.findById(id);

        return employee.orElseThrow(() -> new RecordNotFoundException(
                "Nuk u gjet employee me kete id"));

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
        else throw new RuntimeException("Nuk u gjet employee me kete id");

        return projectDTOS;
    }

    @Override
    public Integer createEmployee(Employees employee) {

        Employees existingEmployee = employeesRepository
                .findById(employee.getEmployee_id()).orElse(null);

        if (existingEmployee==null) {
            Employees createEmployee = createEmployeeEntity(employee);
            employeesRepository.save(createEmployee);
            return createEmployee.getEmployee_id();
        }
        else throw new RecordAlreadyExists("This employee exists");
    }

    @Override
    public Employees updateEmployee(Employees employee, Integer id) {

        Optional<Employees> employees = employeesRepository.findById(id);

        if (employees.isPresent()){

            Employees updateEmployee = employees.get();
            updateEmployee.setSalary(employee.getSalary());
            employeesRepository.save(updateEmployee);
            return updateEmployee;

        }else throw  new RecordNotFoundException("Nuk u gjet employee me kete id");
    }

    @Override
    public Integer deleteEmployee(Integer id) {

        Optional<Employees> employee = employeesRepository.findById(id);

        if (employee.isPresent()){

            Employees deleteEmployee = employee.get();
            employeesRepository.delete(deleteEmployee);
            return id;

        }else throw  new RecordNotFoundException("Nuk u gjet employee me kete id");
    }

    private Employees createEmployeeEntity(Employees employee){

        Employees newEmployee = new Employees();
        newEmployee.setEmployee_id(employee.getEmployee_id());
        newEmployee.setFirst_name(employee.getFirst_name());
        newEmployee.setLast_name(employee.getLast_name());
        newEmployee.setJob_title(employee.getJob_title());
        newEmployee.setSalary(employee.getSalary());
        newEmployee.setDepartment(employee.getDepartment());
//        newEmployee.setProjects(employee.getProjects());
        return newEmployee;
    }
}
