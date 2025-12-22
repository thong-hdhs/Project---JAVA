package com.example.labOdc.DTO.Response;

import java.time.LocalDateTime;

import com.example.labOdc.Model.User;
import com.example.labOdc.Model.UserRole;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String id;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String avatarUrl;
    private UserRole role;
    private Boolean isActive;
    private Boolean emailVerified;
    private LocalDateTime emailVerifiedAt;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserResponse fromUser(User user) {
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .avatarUrl(user.getAvatarUrl())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .emailVerified(user.getEmailVerified())
                .emailVerifiedAt(user.getEmailVerifiedAt())
                .lastLoginAt(user.getLastLoginAt())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
        return userResponse;
    }
}
