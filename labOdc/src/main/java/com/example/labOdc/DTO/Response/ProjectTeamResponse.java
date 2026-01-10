package com.example.labOdc.DTO.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.labOdc.Model.ProjectTeam;
import com.example.labOdc.Model.ProjectTeamStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectTeamResponse {
    private String id;
    private String projectId;
    private String talentId;
    private Boolean isLeader;
    private LocalDate joinedDate;
    private LocalDate leftDate;
    private ProjectTeamStatus status;
    private BigDecimal performanceRating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProjectTeamResponse fromProjectTeam(ProjectTeam pt) {
        return ProjectTeamResponse.builder()
                .id(pt.getId())
                .projectId(pt.getProjectId())
                .talentId(pt.getTalentId())
                .isLeader(pt.getIsLeader())
                .joinedDate(pt.getJoinedDate())
                .leftDate(pt.getLeftDate())
                .status(pt.getStatus())
                .performanceRating(pt.getPerformanceRating())
                .createdAt(pt.getCreatedAt())
                .updatedAt(pt.getUpdatedAt())
                .build();
    }
}