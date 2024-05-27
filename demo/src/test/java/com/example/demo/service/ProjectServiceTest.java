package com.example.demo.service;

import com.example.demo.dataproviders.dto.request.DepartmentDTO;
import com.example.demo.dataproviders.dto.request.ProjectDTO;
import com.example.demo.dataproviders.dto.request.ProjectEmployeeDTO;
import com.example.demo.dataproviders.entities.Departments;
import com.example.demo.dataproviders.entities.Employees;
import com.example.demo.dataproviders.entities.Projects;
import com.example.demo.dataproviders.repositories.EmployeesRepository;
import com.example.demo.dataproviders.repositories.ProjectsRepository;
import com.example.demo.dataproviders.services.impl.ProjectServiceImplementation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectsRepository projectsRepository;

    @Mock
    private EmployeesRepository employeesRepository;

    @InjectMocks
    private ProjectServiceImplementation projectService;

    private Employees employees;
    private Projects projects;

    private ProjectDTO projectDTO;

    @BeforeEach
    public void init(){

        projects = Projects.builder().project_id(1)
                .project_name("Spring")
                .start_date(new Date())
                .end_date(new Date())
                .employees(new ArrayList<>())
                .build();

        projectDTO = ProjectDTO.builder()
                .project_id(projects.getProject_id())
                .project_name(projects.getProject_name())
                .start_date(projects.getStart_date())
                .end_date(projects.getEnd_date())
                .build();

        employees = Employees.builder()
                .employee_id(1)
                .first_name("MiloJani")
                .last_name("Molla")
                .job_title("Intern")
                .salary(5000)
                .projects(Collections.singletonList(projects)).build();

//        projects.setEmployees(Collections.singletonList(employees));
//        employeeDTO = EmployeeDTO.builder()
//                .employee_id(employees.getEmployee_id())
//                .first_name(employees.getFirst_name())
//                .last_name(employees.getLast_name())
//                .job_title(employees.getJob_title())
//                .salary(employees.getSalary())
//                .build();
    }

    @Test
    public void projectsService_getAllProjects_returnProjectDTOS(){

        when(projectsRepository.findById(1)).thenReturn(Optional.ofNullable(projects));

        ProjectDTO projectDTO = projectService.getProjectById(projects.getProject_id());

        Assertions.assertThat(projectDTO).isNotNull();
    }

    @Test
    public void projectsService_createProject_ReturnProjectDTO(){

        when(projectsRepository.findById(1)).thenReturn(Optional.empty());
        when(projectsRepository.save(Mockito.any(Projects.class))).thenReturn(projects);

        ProjectDTO createdProject = projectService.createProject(projectDTO);

        Assertions.assertThat(createdProject).isNotNull();
        Assertions.assertThat(createdProject.getProject_id()).isEqualTo(1);
    }

    @Test
    public void projectsService_updateProject_ReturnProjectDTO(){

        when(projectsRepository.findById(1)).thenReturn(Optional.of(projects));
        when(projectsRepository.save(Mockito.any(Projects.class))).thenReturn(projects);

        ProjectDTO updatedProject = projectService.updateProject(projectDTO,projects.getProject_id());

        Assertions.assertThat(updatedProject).isNotNull();
    }

    @Test
    public void projectsService_deleteProject_ReturnDeletedProjectId(){

        when(projectsRepository.findById(1)).thenReturn(Optional.of(projects));
        Mockito.doNothing().when(projectsRepository).delete(Mockito.any(Projects.class));

        Integer deletedProjectId = projectService.deleteProject(projects.getProject_id());

        Assertions.assertThat(deletedProjectId).isNotNull();
        Assertions.assertThat(deletedProjectId).isEqualTo(1);
    }

    @Test
    public void projectService_AddEmployeeToProject_ReturnProjectEmployeeDTO(){
        when(projectsRepository.findById(1)).thenReturn(Optional.of(projects));
        when(employeesRepository.findById(1)).thenReturn(Optional.of(employees));
        when(projectsRepository.save(Mockito.any(Projects.class))).thenReturn(projects);

        ProjectEmployeeDTO projectEmployeeDTO = projectService.addEmployeeToProject(projects.getProject_id(), employees.getEmployee_id());

        Assertions.assertThat(projectEmployeeDTO).isNotNull();
        Assertions.assertThat(projectEmployeeDTO.getProjectId()).isEqualTo(projects.getProject_id());
        Assertions.assertThat(projectEmployeeDTO.getEmployeeId()).isEqualTo(employees.getEmployee_id());
    }

}
