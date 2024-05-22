package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf((httpSecurityCsrfConfigurer) -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(GET, "api/departments/**", "api/projects/**", "api/employees/**").hasAnyAuthority("ADMIN_READ", "USER_READ")
                        .requestMatchers(POST, "api/departments/**", "api/projects/**", "api/employees/**").hasAuthority("ADMIN_CREATE")
                        .requestMatchers(PUT, "api/departments/**", "api/projects/**", "api/employees/**").hasAuthority("ADMIN_UPDATE")
                        .requestMatchers(DELETE, "api/departments/**", "api/projects/**", "api/employees/**").hasAuthority("ADMIN_DELETE")
                        .requestMatchers("api/roles").hasAuthority("ADMIN_MANAGE")
                        .requestMatchers("api/permissions").hasAuthority("ADMIN_MANAGE")
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/api-docs/**").permitAll()
                        .requestMatchers("api/v1/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
