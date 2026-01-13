package com.example.labOdc.Service.Implement;

import com.example.labOdc.DTO.ReportDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Report;
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

    private final ReportRepository repository;

    @Override
    public Report create(ReportDTO reportDTO) {
        Report report = Report.builder()
                .projectId(reportDTO.getProjectId())
                .mentorId(reportDTO.getMentorId())
                .reportType(reportDTO.getReportType())
                .title(reportDTO.getTitle())
                .content(reportDTO.getContent())
                .reportPeriodStart(reportDTO.getReportPeriodStart())
                .reportPeriodEnd(reportDTO.getReportPeriodEnd())
                .attachmentUrl(reportDTO.getAttachmentUrl())
                .status(Report.Status.DRAFT)
                .build();
        return repository.save(report);
    }

    @Override
    public List<Report> getAll() {
        return repository.findAll();
    }

    @Override
    public Report getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy report"));
    }

    @Override
    public List<Report> getByProject(String projectId) {
        return repository.findByProjectId(projectId);
    }

    @Override
    public List<Report> getByMentor(String mentorId) {
        return repository.findByMentorId(mentorId);
    }

    @Override
    public Report update(String id, ReportDTO reportDTO) {
        Report report = getById(id);

        if (reportDTO.getTitle() != null)
            report.setTitle(reportDTO.getTitle());
        if (reportDTO.getContent() != null)
            report.setContent(reportDTO.getContent());
        if (reportDTO.getReportPeriodStart() != null)
            report.setReportPeriodStart(reportDTO.getReportPeriodStart());
        if (reportDTO.getReportPeriodEnd() != null)
            report.setReportPeriodEnd(reportDTO.getReportPeriodEnd());
        if (reportDTO.getAttachmentUrl() != null)
            report.setAttachmentUrl(reportDTO.getAttachmentUrl());

        return repository.save(report);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    // Mentor/Student submit report
    @Override
    public Report submit(String id) {
        Report report = getById(id);
        report.setStatus(Report.Status.SUBMITTED);
        report.setSubmittedDate(LocalDate.now());
        return repository.save(report);
    }

    // Admin/Mentor review
    @Override
    public Report review(String id, Report.Status status, String reviewer, String notes) {
        Report report = getById(id);
        report.setStatus(status);
        report.setReviewedBy(reviewer);
        report.setReviewedAt(LocalDateTime.now());
        report.setReviewNotes(notes);
        return repository.save(report);
    }
}
