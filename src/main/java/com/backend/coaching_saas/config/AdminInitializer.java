package com.backend.coaching_saas.config;

import com.backend.coaching_saas.entity.Role;
import com.backend.coaching_saas.entity.User;
import com.backend.coaching_saas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner createAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.name}") String name,
            @Value("${app.admin.email}") String email,
            @Value("${app.admin.password}") String password
    ) {

        return args -> {

            if (!userRepository.existsByEmail(email)){

                User admin = new User();

                admin.setName(name);
                admin.setEmail(email);
                admin.setPassword(
                        passwordEncoder.encode(password)
                );
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);

                System.out.println("Initial Admin Created.");
            }
        };
    }
}
