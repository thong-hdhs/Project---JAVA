package com.example.labOdc.Service.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.UserDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.User;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(UserDTO userDTO) {
        User user = User.builder()
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .fullName(userDTO.getFullName())
                .phone(userDTO.getPhone())
                .avatarUrl(userDTO.getAvatarUrl())
                .role(userDTO.getRole())
                .isActive(userDTO.getIsActive() != null ? userDTO.getIsActive() : true)
                .emailVerified(userDTO.getEmailVerified())
                .emailVerifiedAt(userDTO.getEmailVerifiedAt())
                .lastLoginAt(userDTO.getLastLoginAt())
                .role(userDTO.getRole())
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
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ko thay id"));

        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        if (userDTO.getFullName() != null)
            user.setFullName(userDTO.getFullName());
        if (userDTO.getPhone() != null)
            user.setPhone(userDTO.getPhone());
        if (userDTO.getAvatarUrl() != null)
            user.setAvatarUrl(userDTO.getAvatarUrl());

        // nay cho admin
        if (userDTO.getRole() != null)
            user.setRole(userDTO.getRole());
        if (userDTO.getIsActive() != null)
            user.setIsActive(userDTO.getIsActive());

        userRepository.save(user);
        return user;
    }

}
// private String email;
// private String password;
// private String fullName;
// private String phone;
// private String avatarUrl;
// private UserRole role;
// private Boolean isActive;
// private Boolean emailVerified;
// private LocalDateTime emailVerifiedAt;
// private LocalDateTime lastLoginAt;
// private LocalDateTime createdAt;
// private LocalDateTime updatedAt;