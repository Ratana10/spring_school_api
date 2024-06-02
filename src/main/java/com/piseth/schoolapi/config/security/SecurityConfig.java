package com.piseth.schoolapi.config.security;

import com.piseth.schoolapi.config.jwt.JwtAuthFilter;
import com.piseth.schoolapi.users.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;

    private final String[] WHITE_LIST_URLs = {
            "/api/auth/register",
            "/api/auth/login",
            "/api/courses",
            "/api/courses/{id}",
            "/api/promotions",
            "/swagger-ui/**",
    };

    private final String[] STUDENT_LIST_URLs = {
            "/api/student/{studentId}/courses", //View their enrollment courses
    };

    private final String[] ADMIN_LIST_URLs = {
            "/api/student/{studentId}/courses",
            "/api/student/{studentId}/courses",
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers(WHITE_LIST_URLs)
                        .permitAll()
                        .requestMatchers("/api/tests/**")
                        .hasRole(RoleEnum.ADMIN.name())
                        .requestMatchers("/api/categories/**")
                        .hasRole(RoleEnum.ADMIN.name())
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                . addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();


    }
}