//package com.example.demo;
//
//import com.example.demo.core.exceptions.InvalidDataException;
//import com.example.demo.core.exceptions.RecordAlreadyExistsException;
//import com.example.demo.core.exceptions.RecordNotFoundException;
//import com.example.demo.dataproviders.dto.request.EmployeeDTO;
//import com.example.demo.dataproviders.entities.Departments;
//import com.example.demo.dataproviders.services.DepartmentService;
//import com.example.demo.endpoints.rest.DepartmentsController;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//public class DepartmentControllerTest {
//
//    @Mock
//    private DepartmentService departmentService;
//
//    @InjectMocks
//    private DepartmentsController departmentsController;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(departmentsController).build();
//    }
//
//    @Test
//    void testGetAllDepartments() throws Exception {
//        Departments department1 = new Departments(1, "HR");
//        Departments department2 = new Departments(2, "IT");
//        List<Departments> departments = Arrays.asList(department1, department2);
//
//        when(departmentService.getAllDepartments()).thenReturn(departments);
//
//        // Perform the request and capture the result
//        MvcResult result = mockMvc.perform(get("/api/departments/all"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//
//        // Print the response content for debugging
//        String jsonResponse = result.getResponse().getContentAsString();
//        System.out.println("JSON Response: " + jsonResponse);
//
//        // Continue with the original assertions
//        mockMvc.perform(get("/api/departments/all"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].name").value("HR"))
//                .andExpect(jsonPath("$[1].id").value(2))
//                .andExpect(jsonPath("$[1].name").value("IT"));
//
//        verify(departmentService, times(1)).getAllDepartments();
//    }
//
//
//    @Test
//    void testGetAllEmployeesFromDepartment() throws Exception {
//        EmployeeDTO employee1 = new EmployeeDTO(1, "John", "Doe","Intern", 50000);
//        EmployeeDTO employee2 = new EmployeeDTO(2, "Jane", "Doe","Intern", 60000);
//        List<EmployeeDTO> employees = Arrays.asList(employee1, employee2);
//
//        when(departmentService.getAllEmployeesFromDepartment(anyInt())).thenReturn(employees);
//
//        mockMvc.perform(get("/api/departments/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].firstName").value("John"))
//                .andExpect(jsonPath("$[0].lastName").value("Doe"))
//                .andExpect(jsonPath("$[0].salary").value(50000))
//                .andExpect(jsonPath("$[1].id").value(2))
//                .andExpect(jsonPath("$[1].firstName").value("Jane"))
//                .andExpect(jsonPath("$[1].lastName").value("Doe"))
//                .andExpect(jsonPath("$[1].salary").value(60000));
//
//        verify(departmentService, times(1)).getAllEmployeesFromDepartment(anyInt());
//    }
//
//    @Test
//    void testGetTotalEmployeeSalary() throws Exception {
//        when(departmentService.getTotalEmployeeSalary(anyInt())).thenReturn(110000);
//
//        mockMvc.perform(get("/api/departments/salary/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Total salary e departamentit me id: 1 eshte 110000"));
//
//        verify(departmentService, times(1)).getTotalEmployeeSalary(anyInt());
//    }
//
//    @Test
//    void testAddDepartment() throws Exception {
//        Departments department = new Departments(3, "Finance");
//        when(departmentService.addDepartment(any(Departments.class))).thenReturn(3);
//
//        mockMvc.perform(post("/api/departments")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"id\": 3, \"name\": \"Finance\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Departmenti me id:3 u krijua"));
//
//        verify(departmentService, times(1)).addDepartment(any(Departments.class));
//    }
//
//    @Test
//    void testUpdateDepartment() throws Exception {
//        Departments updatedDepartment = new Departments(1, "HR Updated");
//        when(departmentService.updateDepartment(any(Departments.class), anyInt())).thenReturn(updatedDepartment);
//
//        mockMvc.perform(put("/api/departments/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"id\": 1, \"name\": \"HR Updated\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("HR Updated"));
//
//        verify(departmentService, times(1)).updateDepartment(any(Departments.class), anyInt());
//    }
//
//    @Test
//    void testDeleteDepartment() throws Exception {
//        when(departmentService.deleteDepartment(anyInt())).thenReturn(1);
//
//        mockMvc.perform(delete("/api/departments/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Departamenti me id:1 u fshi"));
//
//        verify(departmentService, times(1)).deleteDepartment(anyInt());
//    }
//}
