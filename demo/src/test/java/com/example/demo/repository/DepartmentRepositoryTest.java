package com.example.demo.repository;

import com.example.demo.dataproviders.entities.Departments;
import com.example.demo.dataproviders.repositories.DepartmentsRepository;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentsRepository departmentsRepository;

    @Test
    public void departmentsRepository_getAllDepartments(){

        //Arrange
        Departments departments = Departments.builder()
                .department_id(1).departmentName("Algorhythm")
                .employees(new ArrayList<>()).build();
        //Act
        departmentsRepository.save(departments);
        List<Departments> getAllDepartments = departmentsRepository.findAll();
        //Assert
        Assertions.assertThat(getAllDepartments).isNotNull();
        Assertions.assertThat(getAllDepartments.size()).isEqualTo(1);
    }

    @Test
    public void departmentsRepository_getDepartmentById(){
        Departments departments = Departments.builder()
                .department_id(1).departmentName("Algorhythm")
                .employees(new ArrayList<>()).build();
        departmentsRepository.save(departments);
        Departments getDepartment=departmentsRepository
                .findById(departments.getDepartment_id()).get();
        Assertions.assertThat(getDepartment).isNotNull();
    }

    @Test
    public void departmentsRepository_findBy(){
        Departments departments = Departments.builder()
                .department_id(1).departmentName("Algorhythm")
                .employees(new ArrayList<>()).build();
        departmentsRepository.save(departments);
        Departments getDepartment=departmentsRepository
                .findByDepartmentName(departments.getDepartmentName()).get();
        Assertions.assertThat(getDepartment).isNotNull();
    }

    @Test
    public void departmentRepository_updateDepartment(){
        Departments departments = Departments.builder()
                .department_id(1).departmentName("Algorhythm")
                .employees(new ArrayList<>()).build();
        departmentsRepository.save(departments);

        Departments departmentSave=departmentsRepository
                .findById(departments.getDepartment_id()).get();
        departmentSave.setDepartmentName("Algorhythm-i");

        Departments updateDepartment=departmentsRepository.save(departmentSave);

        Assertions.assertThat(updateDepartment.getDepartmentName()).isNotNull();

    }

    @Test
    public void departmentRepository_deleteDepartment(){
        Departments departments = Departments.builder()
                .department_id(1).departmentName("Algorhythm")
                .employees(new ArrayList<>()).build();
        departmentsRepository.save(departments);

        departmentsRepository.deleteById(departments.getDepartment_id());
        Optional<Departments> department = departmentsRepository.findById(departments.getDepartment_id());

        Assertions.assertThat(department).isEmpty();
    }
}
