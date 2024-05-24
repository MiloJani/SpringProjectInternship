package com.example.demo.service;

import com.example.demo.dataproviders.dto.request.DepartmentDTO;
import com.example.demo.dataproviders.entities.Departments;
import com.example.demo.dataproviders.repositories.DepartmentsRepository;
import com.example.demo.dataproviders.services.impl.DepartmentServiceImplementation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentsRepository departmentsRepository;


    @InjectMocks
    private DepartmentServiceImplementation departmentService;

    @Test
    public void departmentService_createDepartment_ReturnDepartmentDTO(){
        Departments departments = Departments.builder()
                .department_id(1)
                .departmentName("Algorhythm")
                .build();

        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .departmentId(departments.getDepartment_id())
                .departmentName(departments.getDepartmentName())
                .build();

        when(departmentsRepository.save(Mockito.any(Departments.class))).thenReturn(departments);

        DepartmentDTO savedDepartment = departmentService.addDepartment(departments);

        Assertions.assertThat(savedDepartment).isNotNull();
    }

    @Test
    public void departmentService_getAllDepartments_returnDepartments(){

        Departments departments = Departments.builder()
                .department_id(1)
                .departmentName("Algorhythm")
                .build();

        List<Departments> departmentsAll = List.of(departments);

        Mockito.when(departmentsRepository.findAll()).thenReturn(departmentsAll);
        List<Departments> departmentsList = departmentService.getAllDepartments();

        Assertions.assertThat(departmentsList).isNotEmpty();
    }

    @Test
    public void departmentService_getById_ReturnDepartmentDTO(){
        Departments departments = Departments.builder()
                .department_id(1)
                .departmentName("Algorhythm")
                .build();

        when(departmentsRepository.findById(1)).thenReturn(Optional.of(departments));

        DepartmentDTO foundDepartment = departmentService.getDepartmentById(1);

        Assertions.assertThat(foundDepartment).isNotNull();
    }

    @Test
    public void departmentService_updateDepartment_ReturnDepartmentDTO(){
        Departments departments = Departments.builder()
                .department_id(1)
                .departmentName("Algorhythm")
                .build();

        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .departmentId(departments.getDepartment_id())
                .departmentName(departments.getDepartmentName())
                .build();

        when(departmentsRepository.save(Mockito.any(Departments.class))).thenReturn(departments);
        when(departmentsRepository.findById(1)).thenReturn(Optional.of(departments));
        DepartmentDTO savedDepartment = departmentService.updateDepartment(departments,1);

        Assertions.assertThat(savedDepartment).isNotNull();
    }

    @Test
    public void departmentService_deleteDepartment_ReturnId(){

        Departments departments = Departments.builder()
                .department_id(1)
                .departmentName("Algorhythm")
                .build();

        when(departmentsRepository.findById(1)).thenReturn(Optional.of(departments));
        Mockito.doNothing().when(departmentsRepository).delete(Mockito.any(Departments.class));

        Integer deletedDepId = departmentService.deleteDepartment(1);

        Assertions.assertThat(deletedDepId).isNotNull();
        Assertions.assertThat(deletedDepId).isEqualTo(departments.getDepartment_id());
        //assertAll--nqs psh delete kthen void
    }

}
