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
    private PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository,
            RoleRepository roleRepository) {
        return args -> {

            // ================================
            // 1. Seed role USER nếu chưa tồn tại
            // ================================
            RoleEntity userRole = roleRepository.findByRole(UserRole.USER)
                    .orElseGet(() -> {
                        log.info("Creating default USER role...");
                        return roleRepository.save(RoleEntity.builder()
                                .role(UserRole.USER)
                                .build());
                    });

            // ================================
            // 2. Seed role SYSTEM_ADMIN nếu chưa tồn tại
            // ================================
            RoleEntity systemAdminRole = roleRepository.findByRole(UserRole.SYSTEM_ADMIN)
                    .orElseGet(() -> {
                        log.info("Creating SYSTEM_ADMIN role as it doesn't exist...");
                        return roleRepository.save(RoleEntity.builder()
                                .role(UserRole.SYSTEM_ADMIN)
                                .build());
                    });

            // ================================
            // 3. Tạo user admin nếu chưa có
            // ================================
            if (userRepository.findByUsername("admin").isEmpty()) {

                User adminUser = User.builder()
                        .username("admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .isActive(true)
                        .emailVerified(true)
                        .build();

                // Gán role SYSTEM_ADMIN
                adminUser.getRoles().add(systemAdminRole);

                userRepository.save(adminUser);
                log.warn("ADMIN user created successfully with SYSTEM_ADMIN role");
            }
        };
    }
}
