package com.example.demo.controller;

import com.example.demo.core.exceptions.InvalidDataException;
import com.example.demo.core.exceptions.RecordAlreadyExistsException;
import com.example.demo.core.exceptions.RecordNotFoundException;
import com.example.demo.dataproviders.dto.request.PermissionDTO;
import com.example.demo.dataproviders.dto.request.ProjectDTO;
import com.example.demo.dataproviders.entities.Permissions;
import com.example.demo.dataproviders.services.PermissionsService;
import com.example.demo.endpoints.rest.PermissionsController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PermissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PermissionsService permissionsService;

    @InjectMocks
    private PermissionsController permissionsController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
//    @WithMockUser(authorities = "ADMIN_MANAGE")
    public void getAllPermissions_ReturnListOfPermissions() throws Exception {
        Permissions permission1 = new Permissions();
        permission1.setPermissionId(1);
        Permissions permission2 = new Permissions();
        permission2.setPermissionId(2);
        List<Permissions> permissions = Arrays.asList(permission1, permission2);

        when(permissionsService.getAllPermissions()).thenReturn(permissions);

        mockMvc.perform(get("/api/permissions"))
                .andExpect(status().isOk());
    }

    @Test
    public void createPermission_ReturnPermissionDTO() throws Exception {
        Permissions permission = new Permissions();
        permission.setPermissionId(1);
        permission.setPermissionName("ADMIN_CREATE");

        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setPermissionId(1);
        permissionDTO.setPermissionName(permission.getPermissionName());

        given(permissionsService.createPermission(ArgumentMatchers.any(Permissions.class)))
                .willReturn(permissionDTO);

        mockMvc.perform(post("/api/permissions/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(permission)))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePermission_ReturnPermission() throws Exception {
        Permissions permission = new Permissions();

        when(permissionsService.updatePermission(ArgumentMatchers.any(Permissions.class), ArgumentMatchers.anyInt())).thenReturn(permission);

        mockMvc.perform(put("/api/permissions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(permission)))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePermission_ReturnIdOfDeletedPermission() throws Exception {
        when(permissionsService.deletePermission(ArgumentMatchers.anyInt())).thenReturn(1);

        mockMvc.perform(delete("/api/permissions/1"))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
