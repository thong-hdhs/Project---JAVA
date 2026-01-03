package com.example.labOdc.Controller;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.ReportDTO;
import com.example.labOdc.DTO.Response.ReportResponse;
import com.example.labOdc.Model.Report;
import com.example.labOdc.Service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@AllArgsConstructor
public class ReportController {

    private final ReportService service;

    @PostMapping
    public ApiResponse<ReportResponse> create(@RequestBody ReportDTO reportDTO) {
        return ApiResponse.success(
                ReportResponse.fromEntity(service.create(reportDTO)),
                "Tạo report thành công",
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ApiResponse<List<ReportResponse>> getAll() {
        return ApiResponse.success(
                service.getAll().stream()
                        .map(ReportResponse::fromEntity)
                        .toList(),
                "OK",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<ReportResponse> getById(@PathVariable String id) {
        return ApiResponse.success(
                ReportResponse.fromEntity(service.getById(id)),
                "OK",
                HttpStatus.OK
        );
    }

    @GetMapping("/project/{projectId}")
    public ApiResponse<List<ReportResponse>> getByProject(@PathVariable String projectId) {
        return ApiResponse.success(
                service.getByProject(projectId).stream()
                        .map(ReportResponse::fromEntity)
                        .toList(),
                "OK",
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<ReportResponse> update(
            @PathVariable String id,
            @RequestBody ReportDTO reportDTO) {

        return ApiResponse.success(
                ReportResponse.fromEntity(service.update(id, reportDTO)),
                "Cập nhật thành công",
                HttpStatus.OK
        );
    }

    @PostMapping("/{id}/submit")
    public ApiResponse<ReportResponse> submit(@PathVariable String id) {
        return ApiResponse.success(
                ReportResponse.fromEntity(service.submit(id)),
                "Đã gửi report",
                HttpStatus.OK
        );
    }

    @PostMapping("/{id}/review")
    public ApiResponse<ReportResponse> review(
            @PathVariable String id,
            @RequestParam Report.Status status,
            @RequestParam String reviewer,
            @RequestParam(required = false) String notes) {

        return ApiResponse.success(
                ReportResponse.fromEntity(service.review(id, status, reviewer, notes)),
                "Đã review",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.success("Xóa thành công", "OK", HttpStatus.OK);
    }
}
