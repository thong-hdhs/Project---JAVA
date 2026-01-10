package com.example.labOdc.DTO.Response;

import java.time.LocalDateTime;

import com.example.labOdc.Model.LabAdmin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LabAdminResponse {
    private String id;
    private String userId;
    private String department;
    private String position;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static LabAdminResponse fromLabAdmin(LabAdmin l) {
        return LabAdminResponse.builder()
                .id(l.getId())
                .userId(l.getUser() != null ? l.getUser().getId() : null)
                .department(l.getDepartment())
                .position(l.getPosition())
                .createdAt(l.getCreatedAt())
                .updatedAt(l.getUpdatedAt())
                .build();
    }
}
