package com.example.labOdc.Service.Implement;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.UserDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.RoleEntity;
import com.example.labOdc.Model.User;
import com.example.labOdc.Model.UserRole;
import com.example.labOdc.Repository.RoleRepository;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserDTO userDTO) {
        Set<RoleEntity> roles;

        if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
            // gán default role = USER
            RoleEntity defaultRole = roleRepository.findByRole(UserRole.USER)
                    .orElseThrow(() -> new ResourceNotFoundException("Default role USER not found"));
            roles = Set.of(defaultRole);
        } else {
            roles = userDTO.getRoles().stream()
                    .map(code -> {
                        UserRole userRole = UserRole.valueOf(code);
                        return roleRepository.findByRole(userRole)
                                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + code));
                    })
                    .collect(Collectors.toSet());
        }
        User user = User.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .fullName(userDTO.getFullName())
                .phone(userDTO.getPhone())
                .avatarUrl(userDTO.getAvatarUrl())
                .roles(roles)
                .isActive(userDTO.getIsActive() != null ? userDTO.getIsActive() : true)
                .emailVerified(userDTO.getEmailVerified())
                .emailVerifiedAt(userDTO.getEmailVerifiedAt())
                .lastLoginAt(userDTO.getLastLoginAt())
                .build();
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> getAllUser() {
        List<User> list = userRepository.findAll();
        return list;
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);

    }

    @Override
    public User getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ko thay id"));
        return user;
    }

    @Override
    public User updateUser(UserDTO userDTO, String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ko thay id"));

        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }

        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if (userDTO.getFullName() != null) {
            user.setFullName(userDTO.getFullName());
        }

        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }

        if (userDTO.getPhone() != null) {
            user.setPhone(userDTO.getPhone());
        }

        if (userDTO.getAvatarUrl() != null) {
            user.setAvatarUrl(userDTO.getAvatarUrl());
        }

        // ===============================
        // CẬP NHẬT ROLES NẾU CÓ
        // ===============================
        if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
            Set<RoleEntity> newRoles = userDTO.getRoles().stream()
                    .map(code -> {
                        UserRole userRole = UserRole.valueOf(code);
                        return roleRepository.findByRole(userRole)
                                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + code));
                    })
                    .collect(Collectors.toSet());
            user.setRoles(newRoles);
        }

        // update isActive
        if (userDTO.getIsActive() != null) {
            user.setIsActive(userDTO.getIsActive());
        }

        return userRepository.save(user);
    }

}
