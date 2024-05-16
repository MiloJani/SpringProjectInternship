package com.example.demo.dataproviders.services.impl;

import com.example.demo.dataproviders.dto.request.AuthenticationRequest;
import com.example.demo.dataproviders.dto.request.RegisterRequest;
import com.example.demo.dataproviders.dto.response.AuthenticationResponse;
import com.example.demo.dataproviders.entities.Role;
import com.example.demo.dataproviders.entities.User;
import com.example.demo.dataproviders.repositories.RoleRepository;
import com.example.demo.dataproviders.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    public AuthenticationResponse register(RegisterRequest request){
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();


        List<Role> roles = new ArrayList<>();
        request.getRoles().forEach(r -> {
            Role role = roleRepository.findById(r.getRoleId()).orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + r.getRoleId()));
            roles.add(role);
            role.getUsers().add(user);
        });
        user.setRoles(roles);

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        System.out.println(user);
        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        List<String> roleNames = new ArrayList<>();
        user.getRoles().forEach(r -> roleNames.add(r.getRoleName()));
        System.out.println(roleNames);
        System.out.println(user);
        var jwtToken = jwtService.generateToken(roleNames,user);
        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
