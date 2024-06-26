package com.example.demo.dataproviders.services.impl;

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
import com.example.demo.dataproviders.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    @Override
    public AuthenticationResponse register(RegisterRequest request) throws RecordAlreadyExistsException, RecordNotFoundException {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RecordAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        List<Role> roles = new ArrayList<>();
        for (RoleDTO roleDTO : request.getRoles()) {
            Role role = roleRepository.findById(roleDTO.getRoleId()).orElseThrow(() -> new RecordNotFoundException("Role not found"));
            roles.add(role);
            role.getUsers().add(user);
        }
        user.setRoles(roles);

        userRepository.save(user);

        List<String> roleNames = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
        List<String> permissions = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permissions::getPermissionName)
                .collect(Collectors.toList());

        var jwtToken = jwtService.generateToken(new HashMap<>(), user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new AuthenticationFailedException("User not found with this email: " + request.getEmail()));

            List<String> roleNames = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
            List<String> permissions = user.getRoles().stream()
                    .flatMap(role -> role.getPermissions().stream())
                    .map(Permissions::getPermissionName)
                    .collect(Collectors.toList());

            var jwtToken = jwtService.generateToken(new HashMap<>(), user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            throw new AuthenticationFailedException("Authentication failed for user: " + request.getEmail());
        }
    }
}
