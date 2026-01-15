package com.example.labOdc.DTO;

import com.example.labOdc.Model.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDTO {

    private String projectId;
    private String mentorId;
    private Report.ReportType reportType;
    private String title;
    private String content;
    private LocalDate reportPeriodStart;
    private LocalDate reportPeriodEnd;
    private LocalDate submittedDate;
    private Report.Status status;
    private String reviewedBy;
    private LocalDateTime reviewedAt;
    private String reviewNotes;
    private String attachmentUrl;
}
