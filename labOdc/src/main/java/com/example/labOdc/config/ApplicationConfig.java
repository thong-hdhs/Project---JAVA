package com.example.labOdc.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.labOdc.Model.RoleEntity;
import com.example.labOdc.Model.User;
import com.example.labOdc.Model.UserRole;
import com.example.labOdc.Repository.RoleRepository;
import com.example.labOdc.Repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j

public class ApplicationConfig {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {

                RoleEntity systemAdminRole = roleRepository.findByRole(UserRole.SYSTEM_ADMIN)
                        .orElseThrow(() -> new RuntimeException("SYSTEM_ADMIN role not found"));

                User user = User.builder()
                        .username("admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .roles(Set.of(systemAdminRole))
                        .isActive(true)
                        .emailVerified(true)
                        .build();

                userRepository.save(user);
                log.warn("ADMIN user created");
            }
        };
    }

}
