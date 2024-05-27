package com.example.demo.service;

import com.example.demo.dataproviders.dto.request.DepartmentDTO;
import com.example.demo.dataproviders.dto.request.EmployeeDTO;
import com.example.demo.dataproviders.dto.request.ProjectDTO;
import com.example.demo.dataproviders.entities.Departments;
import com.example.demo.dataproviders.entities.Employees;
import com.example.demo.dataproviders.entities.Projects;
import com.example.demo.dataproviders.repositories.DepartmentsRepository;
import com.example.demo.dataproviders.repositories.EmployeesRepository;
import com.example.demo.dataproviders.repositories.ProjectsRepository;
import com.example.demo.dataproviders.services.impl.EmployeeServiceImplementation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private ProjectsRepository projectsRepository;

    @Mock
    private DepartmentsRepository departmentsRepository;

    @Mock
    private EmployeesRepository employeesRepository;

    @InjectMocks
    private EmployeeServiceImplementation employeeServiceImplementation;

    private Departments departments;
    private Employees employees;

    private Projects projects;
    private EmployeeDTO employeeDTO;
    private DepartmentDTO departmentDTO;

    @BeforeEach
    public void init(){

        departments = Departments.builder()
                .department_id(1)
                .departmentName("Algorhythm")
                .employees(new ArrayList<>()).build();

        departmentDTO = DepartmentDTO.builder()
                .departmentId(departments.getDepartment_id())
                .departmentName(departments.getDepartmentName())
                .build();

        projects = Projects.builder().project_id(1)
                .project_name("Spring")
                .start_date(new Date())
                .end_date(new Date())
                .build();

        employees = Employees.builder()
                .employee_id(1)
                .first_name("MiloJani")
                .last_name("Molla")
                .job_title("Intern")
                .salary(5000)
                .department(departments)
                .projects(Collections.singletonList(projects)).build();

//        employeeDTO = EmployeeDTO.builder()
//                .employee_id(employees.getEmployee_id())
//                .first_name(employees.getFirst_name())
//                .last_name(employees.getLast_name())
//                .job_title(employees.getJob_title())
//                .salary(employees.getSalary())
//                .build();
    }

    @Test
    public void EmployeeService_createEmployee_ReturnsEmployeeDTO(){
        when(employeesRepository.findById(employees.getEmployee_id())).thenReturn(Optional.empty());
        when(employeesRepository.save(Mockito.any(Employees.class))).thenReturn(employees);

        EmployeeDTO savedEmployee = employeeServiceImplementation.createEmployee(employees);

        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getEmployee_id()).isEqualTo(employees.getEmployee_id());
        Assertions.assertThat(savedEmployee.getFirst_name()).isEqualTo(employees.getFirst_name());
        Assertions.assertThat(savedEmployee.getLast_name()).isEqualTo(employees.getLast_name());
        Assertions.assertThat(savedEmployee.getJob_title()).isEqualTo(employees.getJob_title());
        Assertions.assertThat(savedEmployee.getSalary()).isEqualTo(employees.getSalary());
    }

    @Test
    public void EmployeeService_getEmployeeById_ReturnEmployeeDTO(){
        when(employeesRepository.findById(1)).thenReturn(Optional.ofNullable(employees));

        EmployeeDTO employee = employeeServiceImplementation.getEmployeeById(1);

        Assertions.assertThat(employee).isNotNull();
    }

    @Test
    public void EmployeeService_getEmployeesProjects_ReturnListOfProjectDTO(){

        when(employeesRepository.findById(1)).thenReturn(Optional.ofNullable(employees));

        List<ProjectDTO> projectDTOS = employeeServiceImplementation.getAllEmployeeProjects(1);

        Assertions.assertThat(projectDTOS).isNotEmpty();
    }

    @Test
    public void EmployeeService_updateEmployee_ReturnEmployeeDTO(){

        when(employeesRepository.findById(1)).thenReturn(Optional.ofNullable(employees));
        when(employeesRepository.save(Mockito.any(Employees.class))).thenReturn(employees);

        EmployeeDTO updatedEmployee = employeeServiceImplementation.updateEmployee(employees,employees.getEmployee_id());

        Assertions.assertThat(updatedEmployee).isNotNull();
    }

    @Test
    public void EmployeeService_deleteEmployee_returnIdOfDeletedEmployee(){

        when(employeesRepository.findById(1)).thenReturn(Optional.ofNullable(employees));
        Mockito.doNothing().when(employeesRepository).delete(Mockito.any(Employees.class));

        Integer deletedEmployeeId = employeeServiceImplementation.deleteEmployee(employees.getEmployee_id());

        Assertions.assertThat(deletedEmployeeId).isNotNull();
        Assertions.assertThat(deletedEmployeeId).isEqualTo(employees.getEmployee_id());

    }
}
