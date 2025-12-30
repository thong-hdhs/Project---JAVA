package com.example.labOdc.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabAdminDTO {
    private String userId;
    private String department;
    private String position;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
