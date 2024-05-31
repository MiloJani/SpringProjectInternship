package com.example.demo.controller;

import com.example.demo.core.exceptions.RecordAlreadyExistsException;
import com.example.demo.core.exceptions.RecordNotFoundException;
import com.example.demo.dataproviders.dto.request.AuthenticationRequest;
import com.example.demo.dataproviders.dto.request.RegisterRequest;
import com.example.demo.dataproviders.dto.response.AuthenticationResponse;
import com.example.demo.dataproviders.services.UserService;
import com.example.demo.endpoints.rest.AuthenticationController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this); //intializes objects with @Mock,@InjectMock
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();//creates an instance
    }

    @Test
    public void testRegister() throws Exception {
        RegisterRequest request = new RegisterRequest();

        AuthenticationResponse response = new AuthenticationResponse();

        when(userService.register(ArgumentMatchers.any(RegisterRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthenticate() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest();

        AuthenticationResponse response = new AuthenticationResponse();

        when(userService.authenticate(ArgumentMatchers.any(AuthenticationRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
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
