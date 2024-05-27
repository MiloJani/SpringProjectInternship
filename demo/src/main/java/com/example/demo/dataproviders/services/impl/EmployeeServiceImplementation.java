package com.example.demo.dataproviders.services.impl;

import com.example.demo.core.exceptions.InvalidDataException;
import com.example.demo.core.exceptions.RecordAlreadyExistsException;
import com.example.demo.dataproviders.dto.request.EmployeeDTO;
import com.example.demo.dataproviders.entities.Employees;
import com.example.demo.dataproviders.repositories.EmployeesRepository;
import com.example.demo.dataproviders.services.EmployeeService;
import com.example.demo.dataproviders.dto.request.ProjectDTO;
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
    public List<EmployeeDTO> getAllEmployees() {

        List<Employees> employees = employeesRepository.findAll();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employees employee: employees){
            employeeDTOS.add(mapToDto(employee));
        }
        return employeeDTOS;
    }

    @Override
    public EmployeeDTO getEmployeeById(Integer id) throws InvalidDataException,RecordNotFoundException {

        if (id<=0) {
            throw new InvalidDataException("Id value is not acceptable");
        }
        Optional<Employees> employees = employeesRepository.findById(id);

        if (employees.isPresent()){
            Employees employee = employees.get();
            return mapToDto(employee);
        }else throw new RecordNotFoundException( "Nuk u gjet employee me kete id");

    }

    @Override
    public List<ProjectDTO> getAllEmployeeProjects(Integer id) throws InvalidDataException,RecordNotFoundException{

        if (id<=0) {
            throw new InvalidDataException("Id value is not acceptable");
        }

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
        else throw new RecordNotFoundException("Nuk u gjet employee me kete id");

        return projectDTOS;
    }

    @Override
    public EmployeeDTO createEmployee(Employees employee) throws RecordAlreadyExistsException {

        Employees existingEmployee = employeesRepository
                .findById(employee.getEmployee_id()).orElse(null);

        if (existingEmployee==null) {
            Employees createEmployee = createEmployeeEntity(employee);
            Employees savedEmployee = employeesRepository.save(createEmployee);
            return mapToDto(savedEmployee);
        }
        else throw new RecordAlreadyExistsException("This employee exists");
    }

    @Override
    public EmployeeDTO updateEmployee(Employees employee, Integer id) throws InvalidDataException,RecordNotFoundException {

        if (id<=0) {
            throw new InvalidDataException("Id value is not acceptable");
        }

        Optional<Employees> employees = employeesRepository.findById(id);

        if (employees.isPresent()){

//            if (employee.getFirst_name()==null) throw new InvalidDataException("First name is required");
            Employees updateEmployee = employees.get();
            updateEmployee.setSalary(employee.getSalary());
            Employees savedEmployee = employeesRepository.save(updateEmployee);
            return mapToDto(savedEmployee);

        }else throw  new RecordNotFoundException("Nuk u gjet employee me kete id");
    }

    @Override
    public Integer deleteEmployee(Integer id) throws InvalidDataException,RecordNotFoundException{

        if (id<=0) {
            throw new InvalidDataException("Id value is not acceptable");
        }

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

    private EmployeeDTO mapToDto(Employees employee) {
        return EmployeeDTO.builder()
                .employee_id(employee.getEmployee_id())
                .first_name(employee.getFirst_name())
                .last_name(employee.getLast_name())
                .job_title(employee.getJob_title())
                .salary(employee.getSalary())
                .build();
    }

}
