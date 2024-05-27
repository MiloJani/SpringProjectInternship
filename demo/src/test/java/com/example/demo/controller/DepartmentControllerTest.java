package com.example.demo.controller;

import com.example.demo.dataproviders.dto.request.DepartmentDTO;
import com.example.demo.dataproviders.dto.request.EmployeeDTO;
import com.example.demo.dataproviders.entities.Departments;
import com.example.demo.dataproviders.services.DepartmentService;
import com.example.demo.dataproviders.services.impl.JwtService;
import com.example.demo.endpoints.rest.DepartmentsController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DepartmentsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private Departments departments;
    private DepartmentDTO departmentDTO;

    private EmployeeDTO employeeDTO1;
    private EmployeeDTO employeeDTO2;

    @BeforeEach
    public void init(){
        departments = Departments.builder()
                .department_id(1)
                .departmentName("Algorhythm")
                .build();
        departmentDTO = DepartmentDTO.builder()
                .departmentId(1)
                .departmentName("Algo")
                .build();
        employeeDTO1 = EmployeeDTO.builder()
                .first_name("Milo")
                .last_name("Molla")
                .job_title("Intern")
                .salary(5000).build();
        employeeDTO2 = EmployeeDTO.builder()
                .first_name("Jani")
                .last_name("Molla")
                .job_title("Intern")
                .salary(6000).build();
    }

    //MockWithUser
    @Test
    public void DepartmentController_AddDepartment_ReturnCreated() throws Exception{

        given(departmentService.addDepartment(ArgumentMatchers.any(Departments.class)))
                .willReturn(departmentDTO);

        ResultActions response = mockMvc.perform(post("/api/departments/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departments)));

        response.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.departmentName",
                        CoreMatchers.is(departmentDTO.getDepartmentName())));
//                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void DepartmentController_getDepartmentById_ReturnDepartmentDTO() throws Exception {

        when(departmentService.getDepartmentById(1)).thenReturn(departmentDTO);

        mockMvc.perform(get("/api/departments/find/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.departmentId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departmentName").value("Algo"));
    }

    @Test
    public void DepartmentController_getAllDepartments_ReturnListOfDepartmentsDTO() throws Exception {

        List<DepartmentDTO> departmentDTOList = new ArrayList<>();

        DepartmentDTO department1 = new DepartmentDTO();
        department1.setDepartmentId(1);
        department1.setDepartmentName("Department 1");
        departmentDTOList.add(department1);

        when(departmentService.getAllDepartments()).thenReturn(departmentDTOList);

        ResultActions response = mockMvc.perform(get("/api/departments/all")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(departmentDTOList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departmentId", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departmentName", CoreMatchers.is("Department 1")));

    }

    @Test
    public void DepartmentController_getAllEmployeesFromDepartment_returnListOfEmployeeDTO() throws Exception {

        List<EmployeeDTO> employees = Arrays.asList(employeeDTO1, employeeDTO2);
        when(departmentService.getAllEmployeesFromDepartment(1)).thenReturn(employees);

        mockMvc.perform(get("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].first_name").value("Milo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].last_name").value("Molla"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].job_title").value("Intern"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(5000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].first_name").value("Jani"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].last_name").value("Molla"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].job_title").value("Intern"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].salary").value(6000));
    }

    @Test
    public void DepartmentController_getTotalEmployeeSalary_returnString() throws Exception {
        when(departmentService.getTotalEmployeeSalary(1)).thenReturn(11000);

        mockMvc.perform(get("/api/departments/salary/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Total salary e departamentit me id: 1 eshte 11000"));
    }

    @Test
    public void DepartmentController_updateDepartment_ReturnDepartmentDTO() throws Exception {
        DepartmentDTO updatedDepartment = new DepartmentDTO();
        updatedDepartment.setDepartmentId(1);
        updatedDepartment.setDepartmentName("Updated Department");
        when(departmentService.updateDepartment(ArgumentMatchers.any(), ArgumentMatchers.anyInt())).thenReturn(updatedDepartment);
        //                                      departmentDto         , departmentId
        mockMvc.perform(put("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"departmentName\": \"Updated Department\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.departmentId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departmentName").value("Updated Department"));
    }

    @Test
    public void DepartmentController_deleteDepartment_ReturnString() throws Exception {
        when(departmentService.deleteDepartment(1)).thenReturn(1);

        mockMvc.perform(delete("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Departamenti me id:1 u fshi"));
    }


}
