package com.piseth.schoolapi.config.security;

import com.piseth.schoolapi.config.jwt.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.piseth.schoolapi.users.Permission.*;
import static com.piseth.schoolapi.users.Role.ADMIN;
import static com.piseth.schoolapi.users.Role.STUDENT;

//@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;

    private final String[] WHITE_LIST_URLs = {
            "/api/auth/register",
            "/api/auth/login",

    };

    private final String[] SWAGGER_LIST_URLs = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**"
    };
    private final String[] STUDENT_LIST_URLs = {
            "/api/student/{studentId}/courses", //View their enrollment courses
    };

    private final String[] ADMIN_LIST_URLs = {
            "/api/students/**",
//            "/api/categories/**",
            "/api/study-types/**",
            "/api/schedule/**",
            "/api/courses/**",
            "/api/enrollments/**",
            "/api/payments/**",
            "/api/promotions/**",
            "/api/users/**",
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers(WHITE_LIST_URLs)
                        .permitAll()
                        .requestMatchers(SWAGGER_LIST_URLs)
                        .permitAll()

                        .requestMatchers("/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/courses/{id}").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/promotions").permitAll()
//
//                        .requestMatchers("/api/students/**").hasAnyRole(STUDENT.name(), ADMIN.name())
//                        .requestMatchers(HttpMethod.GET, "/api/students/**").hasAuthority(STUDENT_READ.name())
//                        .requestMatchers(HttpMethod.POST, "/api/students/**").hasAuthority(STUDENT_WRITE.name())
//
//                        .requestMatchers("/api/study-types/**").hasRole(ADMIN.name())
//                        .requestMatchers(HttpMethod.GET, "/api/study-types").hasAnyAuthority(STUDY_TYPE_READ.getPermission() )
//                        .requestMatchers(HttpMethod.GET, "/api/study-types/{id}").hasAnyAuthority(STUDY_TYPE_READ.getPermission() )
//                        .requestMatchers(HttpMethod.POST, "/api/study-types").hasAuthority(STUDY_TYPE_WRITE.name())
//
//                        .anyRequest()
//                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}