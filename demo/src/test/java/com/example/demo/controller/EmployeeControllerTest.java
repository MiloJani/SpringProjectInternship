package com.example.demo.controller;

import com.example.demo.dataproviders.dto.request.EmployeeDTO;
import com.example.demo.dataproviders.dto.request.ProjectDTO;
import com.example.demo.dataproviders.entities.Employees;
import com.example.demo.dataproviders.services.EmployeeService;
import com.example.demo.dataproviders.services.impl.JwtService;
import com.example.demo.endpoints.rest.EmployeeController;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employees employee;
    private EmployeeDTO employeeDTO1;
    private EmployeeDTO employeeDTO2;
    private ProjectDTO projectDTO;

    @BeforeEach
    public void init() {
        employee = Employees.builder()
                .employee_id(1)
                .first_name("Milo")
                .last_name("Molla")
                .job_title("Intern")
                .salary(5000)
                .build();

        employeeDTO1 = EmployeeDTO.builder()
                .employee_id(1)
                .first_name("Milo")
                .last_name("Molla")
                .job_title("Intern")
                .salary(5000)
                .build();

        employeeDTO2 = EmployeeDTO.builder()
                .first_name("Jani")
                .last_name("Molla")
                .job_title("Intern")
                .salary(6000)
                .build();

        projectDTO = ProjectDTO.builder()
                .project_id(1)
                .project_name("Spring")
                .build();
    }

    @Test
    public void EmployeeController_AddEmployee_ReturnCreated() throws Exception {
        given(employeeService.createEmployee(ArgumentMatchers.any(Employees.class)))
                .willReturn(employeeDTO1);

        ResultActions response = mockMvc.perform(post("/api/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        response.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name",
                        CoreMatchers.is(employeeDTO1.getFirst_name())));
    }

    @Test
    void EmployeeController_getEmployeeById_ReturnEmployeeDTO() throws Exception {
        when(employeeService.getEmployeeById(1)).thenReturn(employeeDTO1);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee_id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name").value("Milo"));
    }

    @Test
    public void EmployeeController_getAllEmployees_ReturnListOfEmployeeDTO() throws Exception {
        List<EmployeeDTO> employeeDTOList = Arrays.asList(employeeDTO1, employeeDTO2);

        when(employeeService.getAllEmployees()).thenReturn(employeeDTOList);

        ResultActions response = mockMvc.perform(get("/api/employees/all")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(employeeDTOList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].first_name", CoreMatchers.is("Milo")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].first_name", CoreMatchers.is("Jani")));
    }

    @Test
    public void EmployeeController_getAllEmployeesProject_ReturnListOfProjectDTO() throws Exception {
        List<ProjectDTO> projects = Arrays.asList(projectDTO);

        when(employeeService.getAllEmployeeProjects(1)).thenReturn(projects);

        mockMvc.perform(get("/api/employees/projects/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].project_id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].project_name").value("Spring"));
    }

    @Test
    public void EmployeeController_updateEmployee_ReturnEmployeeDTO() throws Exception {
        EmployeeDTO updatedEmployee = EmployeeDTO.builder()
                .first_name("Updated Milo")
                .last_name("Updated Molla") //lol
                .job_title("Updated Intern")
                .salary(7000)
                .build();

        when(employeeService.updateEmployee(ArgumentMatchers.any(), ArgumentMatchers.anyInt()))
                .thenReturn(updatedEmployee);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployee)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name").value("Updated Milo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last_name").value("Updated Molla"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.job_title").value("Updated Intern"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(7000));
    }

    @Test
    public void EmployeeController_deleteEmployee_ReturnString() throws Exception {
        when(employeeService.deleteEmployee(1)).thenReturn(1);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Employee me id: 1 u fshi"));
    }
}
