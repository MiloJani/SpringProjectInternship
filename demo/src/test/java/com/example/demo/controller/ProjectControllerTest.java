package com.example.demo.controller;

import com.example.demo.dataproviders.dto.request.ProjectDTO;
import com.example.demo.dataproviders.dto.request.ProjectEmployeeDTO;
import com.example.demo.dataproviders.entities.Projects;
import com.example.demo.dataproviders.services.ProjectService;
import com.example.demo.dataproviders.services.impl.JwtService;
import com.example.demo.endpoints.rest.ProjectsController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProjectsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private Projects project;
    private ProjectDTO projectDTO1;
    private ProjectDTO projectDTO2;
    private ProjectEmployeeDTO projectEmployeeDTO;

    @BeforeEach
    public void init() {
        project = Projects.builder()
                .project_id(1)
                .project_name("Spring")
                .build();

        projectDTO1 = ProjectDTO.builder()
                .project_id(1)
                .project_name("Spring")
                .build();

        projectDTO2 = ProjectDTO.builder()
                .project_id(2)
                .project_name("Hibernate")
                .build();

        projectEmployeeDTO = ProjectEmployeeDTO.builder()
                .projectId(1)
                .employeeId(1)
                .build();
    }

    @Test
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void ProjectController_createProject_ReturnCreated() throws Exception {
        given(projectService.createProject(ArgumentMatchers.any(ProjectDTO.class)))
                .willReturn(projectDTO1);

        ResultActions response = mockMvc.perform(post("/api/projects/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectDTO1)));

        response.andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("Projekti me id: " + projectDTO1.getProject_id() + " u krijua:" + projectDTO1));
    }

    @Test
    public void ProjectController_getAllProjects_ReturnListOfProjectDTO() throws Exception {
        List<ProjectDTO> projectDTOList = Arrays.asList(projectDTO1, projectDTO2);

        given(projectService.getAllProjects()).willReturn(Arrays.asList(projectDTO1, projectDTO2));

        ResultActions response = mockMvc.perform(get("/api/projects")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(projectDTOList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].project_name", CoreMatchers.is("Spring")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].project_name", CoreMatchers.is("Hibernate")));
    }

    @Test
    public void ProjectController_getProjectById_ReturnProjectDTO() throws Exception {
        given(projectService.getProjectById(1)).willReturn(projectDTO1);

        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.project_id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.project_name").value("Spring"));
    }

    @Test
    public void ProjectController_updateProject_ReturnUpdatedProjectDTO() throws Exception {
        ProjectDTO updatedProject = ProjectDTO.builder()
                .project_id(1)
                .project_name("Updated Spring")
                .build();

        given(projectService.updateProject(ArgumentMatchers.any(ProjectDTO.class), ArgumentMatchers.anyInt()))
                .willReturn(updatedProject);

        mockMvc.perform(put("/api/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProject)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.project_id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.project_name").value("Updated Spring"));
    }

    @Test
    public void ProjectController_deleteProject_ReturnString() throws Exception {
        given(projectService.deleteProject(1)).willReturn(1);

        mockMvc.perform(delete("/api/projects/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Projekti me id: 1 u fshi"));
    }

    @Test
    public void ProjectController_addEmployeeToProject_ReturnConfirmationMessage() throws Exception {
        given(projectService.addEmployeeToProject(1, 1)).willReturn(projectEmployeeDTO);

        mockMvc.perform(post("/api/projects/addEmployee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectEmployeeDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("Employee me id: 1 u shtua ne projektin me id: 1"));
    }
}
