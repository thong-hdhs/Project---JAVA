package com.example.labOdc.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.labOdc.Model.ProjectStatus;
import com.example.labOdc.Model.ValidationStatus;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProjectDTO {
    private String companyId;
    private String mentorId;
    private String projectName;
    private String projectCode;
    private String description;
    private String requirements;

    private BigDecimal budget;
    private Integer durationMonths;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate actualEndDate;

    private ProjectStatus status;
    private ValidationStatus validationStatus;

    private String validatedBy;
    private LocalDateTime validatedAt;
    private String rejectionReason;

    private Integer maxTeamSize;
    private String requiredSkills;

}
