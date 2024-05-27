package com.example.demo.repository;

import com.example.demo.dataproviders.entities.Departments;
import com.example.demo.dataproviders.entities.Employees;
import com.example.demo.dataproviders.repositories.DepartmentsRepository;
import com.example.demo.dataproviders.repositories.EmployeesRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTest {

    private DepartmentsRepository departmentsRepository;
    private EmployeesRepository employeesRepository;

    @Autowired
    public EmployeeRepositoryTest(EmployeesRepository employeesRepository,DepartmentsRepository departmentsRepository) {
        this.employeesRepository = employeesRepository;
        this.departmentsRepository=departmentsRepository;
    }

    @Test
    public void employeesRepository_save_ReturnSavedEmployee(){

        Departments departments = Departments.builder()
                .department_id(1)
                .departmentName("Algorhythm")
                .employees(new ArrayList<>()).build();

        departmentsRepository.save(departments);

        Employees employees = Employees.builder()
                .employee_id(1)
                .first_name("Milo")
                .last_name("Molla")
                .job_title("Intern")
                .salary(5000)
                .department(departments)
                .projects(new ArrayList<>()).build();

        Employees savedEmployee = employeesRepository.save(employees);

        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getEmployee_id()).isGreaterThan(0);
    }

    @Test
    public void employeesRepository_getAll_ReturnListOfEmployees(){

        Departments departments = Departments.builder()
                .department_id(1)
                .departmentName("Algorhythm")
                .employees(new ArrayList<>()).build();

        departmentsRepository.save(departments);

        Employees employees1 = Employees.builder()
                .employee_id(1)
                .first_name("Milo")
                .last_name("Molla")
                .job_title("Intern")
                .salary(5000)
                .department(departments)
                .projects(new ArrayList<>()).build();

        Employees employees2 = Employees.builder()
                .employee_id(2)
                .first_name("Jani")
                .last_name("Molla")
                .job_title("Intern")
                .salary(5000)
                .department(departments)
                .projects(new ArrayList<>()).build();

        employeesRepository.save(employees1);
        employeesRepository.save(employees2);

        List<Employees> employees = employeesRepository.findAll();

        Assertions.assertThat(employees).isNotNull();
        Assertions.assertThat(employees.size()).isEqualTo(2);

    }

    @Test
    public void employeesRepository_findById_ReturnEmployee(){

        Departments departments = Departments.builder()
                .department_id(1)
                .departmentName("Algorhythm")
                .employees(new ArrayList<>()).build();

        departmentsRepository.save(departments);

        Employees employees1 = Employees.builder()
                .employee_id(1)
                .first_name("Milo")
                .last_name("Molla")
                .job_title("Intern")
                .salary(5000)
                .department(departments)
                .projects(new ArrayList<>()).build();

        employeesRepository.save(employees1);

        Employees employees = employeesRepository.findById(employees1.getEmployee_id()).get();

        Assertions.assertThat(employees).isNotNull();

    }

    @Test
    public void employeesRepository_updateEmployee_ReturnEmployee(){

        Departments departments = Departments.builder()
                .department_id(1)
                .departmentName("Algorhythm")
                .employees(new ArrayList<>()).build();

        departmentsRepository.save(departments);

        Employees employees1 = Employees.builder()
                .employee_id(1)
                .first_name("Milo")
                .last_name("Molla")
                .job_title("Intern")
                .salary(5000)
                .department(departments)
                .projects(new ArrayList<>()).build();

        employeesRepository.save(employees1);

        Employees savedEmployee = employeesRepository.findById(employees1.getEmployee_id()).get();
        savedEmployee.setFirst_name("Jani");
        Employees updatedEmployee = employeesRepository.save(savedEmployee);

        Assertions.assertThat(updatedEmployee.getFirst_name()).isNotNull();
        Assertions.assertThat(updatedEmployee.getFirst_name()).isEqualTo("Jani");

    }

    @Test
    public void employeesRepository_deleteEmployee_ReturnEmployeeIsEmpty(){

        Departments departments = Departments.builder()
                .department_id(1)
                .departmentName("Algorhythm")
                .employees(new ArrayList<>()).build();

        departmentsRepository.save(departments);

        Employees employees1 = Employees.builder()
                .employee_id(1)
                .first_name("Milo")
                .last_name("Molla")
                .job_title("Intern")
                .salary(5000)
                .department(departments)
                .projects(new ArrayList<>()).build();

        employeesRepository.save(employees1);

        employeesRepository.delete(employees1);
        Optional<Employees> employee = employeesRepository.findById(employees1.getEmployee_id());

        Assertions.assertThat(employee).isEmpty();

    }






}
