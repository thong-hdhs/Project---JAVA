package com.example.labOdc.DTO;

import java.math.BigDecimal;

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

    // bắt buộc để tạo Mentor vì Mentor entity cần User
    private String userId;

    private String expertise;
    private Integer yearsExperience;
    private String bio;

    // optional
    private BigDecimal rating;
    private Integer totalProjects;
    private Mentor.Status status;
}