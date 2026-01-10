package com.example.labOdc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.labOdc.Model.User;
import com.example.labOdc.Model.UserRole;
import com.example.labOdc.Repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j

public class ApplicationConfig {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User user = User.builder()
                        .username("admin")
                        .email("admin@gmail.com") // ⚠️ BẮT BUỘC
                        .password(passwordEncoder.encode("admin"))
                        .role(UserRole.ADMIN)
                        .build();

                userRepository.save(user);
                log.warn("ADMIN user created");
            }
        };
    }
}
