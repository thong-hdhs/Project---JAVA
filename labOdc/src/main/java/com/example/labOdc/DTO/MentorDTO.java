package com.example.labOdc.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.labOdc.Model.Mentor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MentorDTO {
    private String userId;
    private String expertise;
    private Integer yearsExperience;
    private String bio;
    private BigDecimal rating;
    private Integer totalProjects;
    private Mentor.Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
