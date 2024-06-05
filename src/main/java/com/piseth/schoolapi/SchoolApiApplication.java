package com.piseth.schoolapi;

import com.piseth.schoolapi.config.JwtService;
import com.piseth.schoolapi.users.Role;
import com.piseth.schoolapi.users.User;
import com.piseth.schoolapi.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAware")
public class SchoolApiApplication {
	@Autowired
	private  JwtService jwtService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SchoolApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(
			UserRepository  userRepo,
			PasswordEncoder passwordEncoder
	) {
		return args -> {
			if (userRepo.count() == 0) {
				User admin = User.builder()
						.firstName("ratana")
						.lastName("admin")
						.email("admin@gmail.com")
						.password(passwordEncoder.encode("admin123"))
						.role(Role.ADMIN)
						.build();

				var token = jwtService.generateToken(userRepo.save(admin));
				System.out.println("Admin token: " + token);


				userRepo.save(admin);

				User user = User.builder()
						.firstName("user firstname")
						.lastName("user lastname")
						.email("user@gmail.com")
						.password(passwordEncoder.encode("user123"))
						.role(Role.MANAGER)
						.build();

				token = jwtService.generateToken(userRepo.save(user));
				System.out.println("Student token: " + token);

			}
		};
	}

}
