package com.piseth.schoolapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.piseth.schoolapi.users.Permission.ADMIN_READ;
import static com.piseth.schoolapi.users.Permission.ADMIN_WRITE;
import static com.piseth.schoolapi.users.Role.ADMIN;
import static com.piseth.schoolapi.users.Role.STUDENT;
import static org.springframework.http.HttpMethod.*;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] WHITE_LIST_URL = {
            "/api/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers(WHITE_LIST_URL).permitAll()

                        .requestMatchers("/api/study-types/**").hasRole(ADMIN.name())
                        .requestMatchers(POST, "/api/study-types/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(PUT, "/api/study-types/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(DELETE, "/api/study-types/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(GET, "/api/study-types/**").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(GET, "/api/study-types/{id}").hasAuthority(ADMIN_READ.name())

                        .requestMatchers("/api/categories/**").hasRole(ADMIN.name())
                        .requestMatchers(POST, "/api/categories/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(PUT, "/api/categories/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(DELETE, "/api/categories/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(GET, "/api/categories/**").hasAuthority(ADMIN_READ.name())

                        .requestMatchers(GET, "/api/courses").permitAll()
                        .requestMatchers(GET, "/api/courses/{id}").permitAll()
                        .requestMatchers(GET, "/api/courses/{id}/schedules").permitAll()

                        .requestMatchers("/api/courses/**").hasRole(ADMIN.name())
                        .requestMatchers(POST, "/api/courses/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(PUT, "/api/courses/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(DELETE, "/api/courses/**").hasAuthority(ADMIN_WRITE.name())


                        .requestMatchers("/api/schedules/**").hasRole(ADMIN.name())
                        .requestMatchers(POST, "/api/schedules/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(PUT, "/api/schedules/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(DELETE, "/api/schedules/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(GET, "/api/schedules/**").hasAuthority(ADMIN_READ.name())

                        .requestMatchers("/api/students/**").hasAnyRole(ADMIN.name(), STUDENT.name())
                        .requestMatchers(POST, "/api/students/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(PUT, "/api/students/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(DELETE, "/api/students/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(GET, "/api/students/**").hasAuthority(ADMIN_READ.name())

                        .requestMatchers(GET, "/api/promotions").permitAll()
                        .requestMatchers(GET, "/api/promotions/{id}").permitAll()

                        .requestMatchers("/api/promotions/**").hasRole(ADMIN.name())
                        .requestMatchers(POST, "/api/promotions/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(PUT, "/api/promotions/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(DELETE, "/api/promotions/**").hasAuthority(ADMIN_WRITE.name())

                        .requestMatchers("/api/enrollments/**").hasRole(ADMIN.name())
                        .requestMatchers(POST, "/api/enrollments/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(DELETE, "/api/enrollments/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(GET, "/api/enrollments/**").hasAuthority(ADMIN_READ.name())

                        .requestMatchers("/api/payment/**").hasRole(ADMIN.name())
                        .requestMatchers(POST, "/api/payment/**").hasAuthority(ADMIN_WRITE.name())
                        .requestMatchers(DELETE, "/api/payment/{id}/cancel").hasAuthority(ADMIN_WRITE.name())

                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authenticationProvider(authenticationProvider)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }
}