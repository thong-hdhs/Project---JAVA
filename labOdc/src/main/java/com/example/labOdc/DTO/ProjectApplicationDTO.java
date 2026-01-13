package com.example.labOdc.DTO;

import java.time.LocalDateTime;

import com.example.labOdc.Model.ProjectApplication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectApplicationDTO {
    private String projectId;
    private String talentId;
    private String coverLetter;
    private ProjectApplication.Status status;
    private String reviewedById;
    private LocalDateTime reviewedAt;
    private String rejectionReason;
    private LocalDateTime appliedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
