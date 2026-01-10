package com.example.labOdc.DTO.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.labOdc.Model.Project;
import com.example.labOdc.Model.ProjectStatus;
import com.example.labOdc.Model.ValidationStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectResponse {
    private String id;
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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProjectResponse fromProject(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .companyId(project.getCompanyId())
                .mentorId(project.getMentorId())
                .projectName(project.getProjectName())
                .projectCode(project.getProjectCode())
                .description(project.getDescription())
                .requirements(project.getRequirements())
                .budget(project.getBudget())
                .durationMonths(project.getDurationMonths())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .actualEndDate(project.getActualEndDate())
                .status(project.getStatus())
                .validationStatus(project.getValidationStatus())
                .validatedBy(project.getValidatedBy())
                .validatedAt(project.getValidatedAt())
                .rejectionReason(project.getRejectionReason())
                .maxTeamSize(project.getMaxTeamSize())
                .requiredSkills(project.getRequiredSkills())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}