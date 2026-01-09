package com.example.labOdc.DTO.Response;

import java.time.LocalDateTime;

import com.example.labOdc.Model.ProjectMentor;
import com.example.labOdc.Model.ProjectMentorRole;
import com.example.labOdc.Model.ProjectMentorStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectMentorResponse {
    private String id;
    private String projectId;
    private String mentorId;
    private ProjectMentorRole role;
    private LocalDateTime assignedAt;
    private ProjectMentorStatus status;

    public static ProjectMentorResponse fromProjectMentor(ProjectMentor pm) {
        return ProjectMentorResponse.builder()
                .id(pm.getId())
                .projectId(pm.getProjectId())
                .mentorId(pm.getMentorId())
                .role(pm.getRole())
                .assignedAt(pm.getAssignedAt())
                .status(pm.getStatus())
                .build();
    }
}