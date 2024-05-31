package com.example.demo.service;

import com.example.demo.core.exceptions.AuthenticationFailedException;
import com.example.demo.core.exceptions.RecordAlreadyExistsException;
import com.example.demo.core.exceptions.RecordNotFoundException;
import com.example.demo.dataproviders.dto.request.AuthenticationRequest;
import com.example.demo.dataproviders.dto.request.RegisterRequest;
import com.example.demo.dataproviders.dto.request.RoleDTO;
import com.example.demo.dataproviders.dto.response.AuthenticationResponse;
import com.example.demo.dataproviders.entities.Permissions;
import com.example.demo.dataproviders.entities.Role;
import com.example.demo.dataproviders.entities.User;
import com.example.demo.dataproviders.repositories.RoleRepository;
import com.example.demo.dataproviders.repositories.UserRepository;
import com.example.demo.dataproviders.services.impl.AuthenticationService;
import com.example.demo.dataproviders.services.impl.JwtService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AuthenticationService userService;

    private User user;
    private RegisterRequest registerRequest;
    private AuthenticationRequest authenticationRequest;
    private Role role;
    private RoleDTO roleDTO;

    @BeforeEach
    public void init() {
        user = User.builder()
                .firstname("Milo")
                .lastname("Molla")
                .email("Milo@gmail.com")
                .password("password")
                .build();

        role = Role.builder()
                .roleId(1)
                .roleName("USER")
                .users(new ArrayList<>())
                .permissions(new ArrayList<>())
                .build();

        user.setRoles(Collections.singletonList(role));

        roleDTO = RoleDTO.builder()
                .roleId(1)
                .roleName("USER")
                .build();

        registerRequest = RegisterRequest.builder()
                .firstname("Milo")
                .lastname("Molla")
                .email("Milo@gmail.com")
                .password("password")
                .roles(List.of(roleDTO))
                .build();

        authenticationRequest = AuthenticationRequest.builder()
                .email("Milo@gmail.com")
                .password("password")
                .build();
    }

    @Test
    public void userService_register_ReturnsAuthenticationResponse() throws RecordAlreadyExistsException, RecordNotFoundException {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(HashMap.class), any(User.class))).thenReturn("jwtToken");

        AuthenticationResponse response = userService.register(registerRequest);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getToken()).isEqualTo("jwtToken");
    }

    @Test
    public void userService_authenticate_ReturnsAuthenticationResponse() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(HashMap.class), any(User.class))).thenReturn("jwtToken");

        AuthenticationResponse response = userService.authenticate(authenticationRequest);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getToken()).isEqualTo("jwtToken");
    }


    @Test
    public void userService_register_ThrowsRecordAlreadyExistsException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        Assertions.assertThatThrownBy(() -> userService.register(registerRequest))
                .isInstanceOf(RecordAlreadyExistsException.class)
                .hasMessageContaining("User with email Milo@gmail.com already exists");
    }

    @Test
    public void userService_authenticate_ThrowsAuthenticationFailedException() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationFailedException("Authentication failed for user: Milo@gmail.com"));

        Assertions.assertThatThrownBy(() -> userService.authenticate(authenticationRequest))
                .isInstanceOf(AuthenticationFailedException.class)
                .hasMessageContaining("Authentication failed for user: Milo@gmail.com");
    }
}
