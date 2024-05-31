package com.example.demo.controller;

import com.example.demo.core.exceptions.InvalidDataException;
import com.example.demo.core.exceptions.RecordAlreadyExistsException;
import com.example.demo.core.exceptions.RecordNotFoundException;
import com.example.demo.dataproviders.dto.request.RoleDTO;
import com.example.demo.dataproviders.entities.Role;
import com.example.demo.dataproviders.services.RoleService;
import com.example.demo.endpoints.rest.RoleController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void RoleController_GetAllRoles_returnListOfRoles() throws Exception {
        Role role1 = new Role();
        role1.setRoleId(1);
        Role role2 = new Role();
        role2.setRoleId(2);
        List<Role> roles = Arrays.asList(role1, role2);

        when(roleService.getAllRoles()).thenReturn(roles);

        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk());
    }

    @Test
    public void RoleController_CreateRole_ReturnRoleDTO() throws Exception {
        Role role = new Role();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleId(1);
        roleDTO.setRoleName("ADMIN");

        when(roleService.createRole(ArgumentMatchers.any(Role.class))).thenReturn(roleDTO);

        mockMvc.perform(post("/api/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(role)))
                .andExpect(status().isOk());
    }

    @Test
    public void RoleController_UpdateRole_ReturnRole() throws Exception {
        Role role = new Role();

        when(roleService.updateRole(ArgumentMatchers.any(Role.class), ArgumentMatchers.anyInt())).thenReturn(new Role());

        mockMvc.perform(put("/api/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(role)))
                .andExpect(status().isOk());
    }

    @Test
    public void RoleController_DeleteRole_ReturnIdOfDeletedRole() throws Exception {
        when(roleService.deleteRole(ArgumentMatchers.anyInt())).thenReturn(1);

        mockMvc.perform(delete("/api/roles/1"))
                .andExpect(status().isOk());
    }

    // Utility method to convert object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
