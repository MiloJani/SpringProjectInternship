package com.example.demo.config;

import com.example.demo.dataproviders.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .csrf((httpSecurityCsrfConfigurer)->httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("api/departments/**").hasAuthority("ADMIN")
                                .requestMatchers("api/v1/**").permitAll().anyRequest().authenticated())
                .sessionManagement(sesion->sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
//                                .requestMatchers("/swagger-ui/").permitAll()
//                                .requestMatchers("/api-docs/").permitAll()
//                .requestMatchers(HttpMethod.GET).hasAnyAuthority("ADMIN","USER")
//                .requestMatchers(HttpMethod.DELETE).hasAuthority("ADMIN")
//                .requestMatchers(HttpMethod.PUT).hasAuthority("ADMIN")
//                .requestMatchers(HttpMethod.POST).hasAuthority("ADMIN")
        return http.build();
    }

}
