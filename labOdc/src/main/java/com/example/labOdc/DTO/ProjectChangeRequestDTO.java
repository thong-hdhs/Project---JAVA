package com.example.labOdc.DTO;

import java.time.LocalDate;

import com.example.labOdc.Model.ProjectChangeRequestStatus;
import com.example.labOdc.Model.ProjectChangeRequestType;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectChangeRequestDTO {
    private String projectId;
    private String requestedBy;
    private ProjectChangeRequestType requestType;

    private String reason;
    private String proposedChanges;
    private String impactAnalysis;

    private ProjectChangeRequestStatus status;
    private String approvedBy;

    private LocalDate requestedDate;
    private LocalDate reviewedDate;
    private String reviewNotes;
}