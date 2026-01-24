package com.example.labOdc.Service;

import com.example.labOdc.DTO.ReportDTO;
import com.example.labOdc.Model.Report;

import java.util.List;

public interface ReportService {

    Report createReport(ReportDTO dto, String mentorId);

    List<Report> getAllReports();

    Report getReportById(String id);

    List<Report> getReportsByProject(String projectId);

    List<Report> getReportsByMentor(String mentorId);

    List<Report> getReportsByStatus(Report.Status status);

    Report updateReport(String id, ReportDTO dto);

    void deleteReport(String id);

    Report submitReport(String id);

    Report reviewReport(String id, String adminId, Report.Status status, String reviewNotes);
}