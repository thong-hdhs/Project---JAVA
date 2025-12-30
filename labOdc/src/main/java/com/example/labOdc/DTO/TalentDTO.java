package com.example.labOdc.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.labOdc.Model.Talent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TalentDTO {
    private String userId;
    private String studentCode;
    private String major;
    private Integer year;
    private String skills;
    private String certifications;
    private String portfolioUrl;
    private String githubUrl;
    private String linkedinUrl;
    private BigDecimal gpa;
    private Talent.Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
