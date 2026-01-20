package com.example.labOdc.Service.Implement;

import com.example.labOdc.DTO.ReportDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.LabAdmin;
import com.example.labOdc.Model.Mentor;
import com.example.labOdc.Model.Project;
import com.example.labOdc.Model.Report;
import com.example.labOdc.Repository.LabAdminRepository;
import com.example.labOdc.Repository.MentorRepository;
import com.example.labOdc.Repository.ProjectRepository;
import com.example.labOdc.Repository.ReportRepository;
import com.example.labOdc.Service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ProjectRepository projectRepository;
    private final MentorRepository mentorRepository;
    private final LabAdminRepository labAdminRepository;

    @Override
    public Report createReport(ReportDTO dto, String mentorId) {

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Project"));

        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Mentor"));

        Report report = Report.builder()
                .project(project)
                .mentor(mentor)
                .reportType(dto.getReportType())
                .title(dto.getTitle())
                .content(dto.getContent())
                .reportPeriodStart(dto.getReportPeriodStart())
                .reportPeriodEnd(dto.getReportPeriodEnd())
                .attachmentUrl(dto.getAttachmentUrl())
                .status(Report.Status.DRAFT)
                .build();

        return reportRepository.save(report);
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public Report getReportById(String id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Report"));
    }

    @Override
    public List<Report> getReportsByProject(String projectId) {
        return reportRepository.findByProjectId(projectId);
    }

    @Override
    public List<Report> getReportsByMentor(String mentorId) {
        return reportRepository.findByMentorId(mentorId);
    }

    @Override
    public List<Report> getReportsByStatus(Report.Status status) {
        return reportRepository.findByStatus(status);
    }

    @Override
    public Report updateReport(String id, ReportDTO dto) {

        Report report = getReportById(id);

        if (dto.getTitle() != null) {
            report.setTitle(dto.getTitle());
        }

        if (dto.getContent() != null) {
            report.setContent(dto.getContent());
        }

        if (dto.getReportType() != null) {
            report.setReportType(dto.getReportType());
        }

        if (dto.getReportPeriodStart() != null) {
            report.setReportPeriodStart(dto.getReportPeriodStart());
        }

        if (dto.getReportPeriodEnd() != null) {
            report.setReportPeriodEnd(dto.getReportPeriodEnd());
        }

        if (dto.getAttachmentUrl() != null) {
            report.setAttachmentUrl(dto.getAttachmentUrl());
        }

        return reportRepository.save(report);
    }

    @Override
    public void deleteReport(String id) {
        reportRepository.deleteById(id);
    }

    @Override
    public Report submitReport(String id) {
        Report report = getReportById(id);

        report.setStatus(Report.Status.SUBMITTED);
        report.setSubmittedDate(LocalDate.now());

        return reportRepository.save(report);
    }

    @Override
    public Report reviewReport(String id, String adminId, Report.Status status, String reviewNotes) {

        Report report = getReportById(id);

        LabAdmin admin = labAdminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy LabAdmin"));

        report.setReviewedBy(admin);
        report.setReviewedAt(LocalDateTime.now());
        report.setStatus(status);
        report.setReviewNotes(reviewNotes);

        return reportRepository.save(report);
    }
}