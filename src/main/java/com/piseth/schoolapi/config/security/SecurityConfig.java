package com.piseth.schoolapi.config.security;

import com.piseth.schoolapi.roles.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;

    private final String[] UNSECURE_URLs= {
            "/api/courses/**",
            "/api/promotions/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/api/courses/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/promotions/**").permitAll()
                        .requestMatchers("/api/categories/**").hasRole(RoleEnum.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails user1 = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .authorities(RoleEnum.ADMIN.getAuthorities())
                .build();

        UserDetails user2 = User.builder()
                .username("dara")
                .password(passwordEncoder.encode("123"))
                .authorities(RoleEnum.USER.getAuthorities())
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }
}
