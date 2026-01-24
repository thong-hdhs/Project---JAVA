package com.example.labOdc.Controller;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.ReportDTO;
import com.example.labOdc.DTO.Response.ReportResponse;
import com.example.labOdc.Model.Report;
import com.example.labOdc.Service.ReportService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/{mentorId}")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN')
    or hasAuthority('MENTOR_SUBMIT_REPORT')
""")
    public ApiResponse<ReportResponse> createReport(
            @PathVariable String mentorId,
            @Valid @RequestBody ReportDTO dto) {

        Report report = reportService.createReport(dto, mentorId);

        return ApiResponse.success(
                ReportResponse.fromEntity(report),
                "Tạo report thành công",
                HttpStatus.CREATED);
    }

    @GetMapping("/")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN','MENTOR')
    or hasAuthority('LAB_VIEW_ALL_REPORTS')
""")
    public ApiResponse<List<ReportResponse>> getAllReports() {
        return ApiResponse.success(
                reportService.getAllReports()
                        .stream()
                        .map(ReportResponse::fromEntity)
                        .toList(),
                "Thành công",
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN','MENTOR')
    or hasAuthority('LAB_VIEW_ALL_REPORTS')
""")
    public ApiResponse<ReportResponse> getById(@PathVariable String id) {
        return ApiResponse.success(
                ReportResponse.fromEntity(reportService.getReportById(id)),
                "Thành công",
                HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN','MENTOR')
    or hasAuthority('LAB_VIEW_ALL_REPORTS')
""")
    public ApiResponse<List<ReportResponse>> getByProject(@PathVariable String projectId) {
        return ApiResponse.success(
                reportService.getReportsByProject(projectId)
                        .stream()
                        .map(ReportResponse::fromEntity)
                        .toList(),
                "Thành công",
                HttpStatus.OK);
    }

    @GetMapping("/mentor/{mentorId}")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN','MENTOR')
    or hasAuthority('LAB_VIEW_ALL_REPORTS')
""")
    public ApiResponse<List<ReportResponse>> getByMentor(@PathVariable String mentorId) {
        return ApiResponse.success(
                reportService.getReportsByMentor(mentorId)
                        .stream()
                        .map(ReportResponse::fromEntity)
                        .toList(),
                "Thành công",
                HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<List<ReportResponse>> byStatus(@PathVariable Report.Status status) {
        List<Report> list = reportService.getReportsByStatus(status);
        return ApiResponse.success(list
                        .stream()
                        .map(ReportResponse::fromEntity)
                        .toList(),
                "Thành công",
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN','MENTOR')
    or hasAuthority('MENTOR_SUBMIT_REPORT')
""")
    public ApiResponse<ReportResponse> updateReport(
            @PathVariable String id,
            @RequestBody ReportDTO dto) {

        Report report = reportService.updateReport(id, dto);

        return ApiResponse.success(
                ReportResponse.fromEntity(report),
                "Cập nhật thành công",
                HttpStatus.OK);
    }
    @PutMapping("/{id}/submit")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN','MENTOR')
    or hasAuthority('MENTOR_SUBMIT_REPORT')
""")
    public ApiResponse<ReportResponse> submitReport(@PathVariable String id) {
        return ApiResponse.success(
                ReportResponse.fromEntity(reportService.submitReport(id)),
                "Đã submit report",
                HttpStatus.OK);
    }

    @PutMapping("/{id}/review/{adminId}")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN')
""")
    public ApiResponse<ReportResponse> reviewReport(
            @PathVariable String id,
            @PathVariable String adminId,
            @RequestParam Report.Status status,
            @RequestParam(required = false) String notes) {

        return ApiResponse.success(
                ReportResponse.fromEntity(
                        reportService.reviewReport(id, adminId, status, notes)),
                "Review thành công",
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN', 'MENTOR')
""")
    public ApiResponse<String> deleteReport(@PathVariable String id) {
        reportService.deleteReport(id);
        return ApiResponse.success("Xóa thành công", "OK", HttpStatus.OK);
    }
}