package com.example.labOdc.DTO;

import com.example.labOdc.Model.ProjectMentorRole;
import com.example.labOdc.Model.ProjectMentorStatus;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectMentorDTO {
    private String projectId;
    private String mentorId;
    private ProjectMentorRole role;
    private ProjectMentorStatus status;
}