package com.example.demo.dataproviders.services.impl;

import com.example.demo.core.exceptions.InvalidDataException;
import com.example.demo.core.exceptions.RecordAlreadyExistsException;
import com.example.demo.dataproviders.entities.Departments;
import com.example.demo.dataproviders.entities.Employees;
import com.example.demo.dataproviders.repositories.DepartmentsRepository;
import com.example.demo.dataproviders.services.DepartmentService;
import com.example.demo.dataproviders.dto.request.EmployeeDTO;
import com.example.demo.core.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        return departmentsRepository.findAll();
    }

    @Override
    public List<EmployeeDTO> getAllEmployeesFromDepartment(Integer id) throws InvalidDataException,RecordNotFoundException {

        if (id<=0) {
            throw new InvalidDataException("Id value is not acceptable");
        }
        Optional<Departments> department = departmentsRepository.findById(id);

        List<EmployeeDTO> employeeDTOS = new ArrayList<>();

        if (department.isPresent()){

            Departments foundDepartment = department.get();

            List<Employees> employees = foundDepartment.getEmployees();

            for(Employees entity : employees){

                EmployeeDTO employeeDTO = new EmployeeDTO();
                employeeDTO.setFirst_name(entity.getFirst_name());
                employeeDTO.setLast_name(entity.getLast_name());
                employeeDTO.setJob_title(entity.getJob_title());
                employeeDTO.setSalary(entity.getSalary());

                employeeDTOS.add(employeeDTO);
            }
            return employeeDTOS;

        }
        else throw new RecordNotFoundException("Nuk u gjet departament");
    }

    @Override
    public Integer getTotalEmployeeSalary(Integer id) throws InvalidDataException,RecordNotFoundException{

        if (id<=0) {
            throw new InvalidDataException("Id value is not acceptable");
        }

        Optional<Departments> departments = departmentsRepository.findById(id);

        if (departments.isPresent()){

            Integer totalSum = 0 ;

            Departments department = departments.get();

            List<Employees> employees = department.getEmployees();

            for (Employees employee : employees){

                totalSum += employee.getSalary();
            }

            return totalSum;
        }
        else throw new RecordNotFoundException(
                "Nuk u gjet departament me kete id");
    }

    @Override
    public Integer addDepartment(Departments department) throws RecordAlreadyExistsException {

        Departments existingDepartment = departmentsRepository
                .findById(department.getDepartment_id()).orElse(null);
        if (existingDepartment==null) {
            Departments newDepartment = new Departments();
            newDepartment.setDepartment_id(department.getDepartment_id());
            newDepartment.setDepartment_name(department.getDepartment_name());
            departmentsRepository.save(newDepartment);
            return newDepartment.getDepartment_id();
        }
        else throw new RecordAlreadyExistsException("Department already exists");
    }

    @Override
    public Departments updateDepartment(Departments department, Integer id) throws InvalidDataException,RecordNotFoundException {

        if (id<=0) {
            throw new InvalidDataException("Id value is not acceptable");
        }
        Optional<Departments> departments = departmentsRepository.findById(id);
        if (departments.isPresent()){

            Departments updateDepartment = departments.get();
            updateDepartment.setDepartment_name(department.getDepartment_name());
            departmentsRepository.save(updateDepartment);
            return updateDepartment;
        }
        else throw new RecordNotFoundException(
                "Nuk u gjet departament me kete id");

    }

    @Override
    public Integer deleteDepartment(Integer id) throws InvalidDataException,RecordNotFoundException {

        if (id<=0) {
            throw new InvalidDataException("Id value is not acceptable");
        }
        Optional<Departments> departments = departmentsRepository.findById(id);
        if (departments.isPresent()){
            Departments deleteDepartment = departments.get();
            departmentsRepository.delete(deleteDepartment);
            return id;
        }
        else throw new RecordNotFoundException(
                "Nuk u gjet departament me kete id");
    }
}
