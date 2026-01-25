package com.example.labOdc.Controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.ProjectApplicationDTO;
import com.example.labOdc.DTO.Response.ProjectApplicationResponse;
import com.example.labOdc.Service.ProjectApplicationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/applications")
public class ProjectApplicationController {
    private final ProjectApplicationService applicationService;

    /**
     * Chức năng: Tạo đơn ứng tuyển dự án mới.
     * Service: ProjectApplicationService.createApplication() - Xử lý logic tạo và lưu entity.
     */
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('TALENT', 'SYSTEM_ADMIN')")
    public ApiResponse<ProjectApplicationResponse> create(@Valid @RequestBody ProjectApplicationDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ApiResponse.error(errorMessages);
        }
        ProjectApplicationResponse response = applicationService.createApplication(dto);
        return ApiResponse.success(response, "Created", HttpStatus.CREATED);
    }

    /**
     * Chức năng: Lấy danh sách tất cả đơn ứng tuyển.
     * Service: ProjectApplicationService.getAllApplications() - Truy vấn tất cả entities.
     */
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('LAB_ADMIN', 'COMPANY', 'SYSTEM_ADMIN')")
    public ApiResponse<List<ProjectApplicationResponse>> getAll() {
        List<ProjectApplicationResponse> list = applicationService.getAllApplications();
        return ApiResponse.success(list, "OK", HttpStatus.OK);
    }

    /**
     * Chức năng: Lấy đơn ứng tuyển theo ID.
     * Service: ProjectApplicationService.getApplicationById() - Truy vấn entity theo ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TALENT', 'LAB_ADMIN', 'COMPANY', 'SYSTEM_ADMIN')")
    public ApiResponse<ProjectApplicationResponse> getById(@PathVariable String id) {
        ProjectApplicationResponse response = applicationService.getApplicationById(id);
        return ApiResponse.success(response, "OK", HttpStatus.OK);
    }

    /**
     * Chức năng: Xóa đơn ứng tuyển theo ID.
     * Service: ProjectApplicationService.deleteApplication() - Xử lý xóa entity.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable String id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.ok("Deleted");
    }

    /**
     * Chức năng: Cập nhật đơn ứng tuyển theo ID.
     * Service: ProjectApplicationService.updateApplication() - Xử lý cập nhật entity.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TALENT', 'LAB_ADMIN', 'SYSTEM_ADMIN')")
    public ApiResponse<ProjectApplicationResponse> update(@Valid @RequestBody ProjectApplicationDTO dto, @PathVariable String id) {
        ProjectApplicationResponse response = applicationService.updateApplication(dto, id);
        return ApiResponse.success(response, "Updated", HttpStatus.OK);
    }

    /**
     * Chức năng: Lấy danh sách đơn ứng tuyển theo Project ID.
     * Service: ProjectApplicationService.findByProjectId() - Truy vấn theo projectId.
     */
    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAnyRole('LAB_ADMIN', 'COMPANY', 'SYSTEM_ADMIN')")
    public ApiResponse<List<ProjectApplicationResponse>> getByProject(@PathVariable String projectId) {
        List<ProjectApplicationResponse> list = applicationService.findByProjectId(projectId);
        return ApiResponse.success(list, "OK", HttpStatus.OK);
    }

    /**
     * Chức năng: Lấy danh sách đơn ứng tuyển theo Talent ID.
     * Service: ProjectApplicationService.findByTalentId() - Truy vấn theo talentId.
     */
    @GetMapping("/talent/{talentId}")
    @PreAuthorize("hasAnyRole('TALENT', 'LAB_ADMIN', 'SYSTEM_ADMIN')")
    public ApiResponse<List<ProjectApplicationResponse>> getByTalent(@PathVariable String talentId) {
        List<ProjectApplicationResponse> list = applicationService.findByTalentId(talentId);
        return ApiResponse.success(list, "OK", HttpStatus.OK);
    }

    /**
     * Chức năng: Phê duyệt đơn ứng tuyển dự án.
     * Service: ProjectApplicationService.approveApplication() - Cập nhật trạng thái thành APPROVED.
     */
    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('LAB_ADMIN')")
    public ApiResponse<ProjectApplicationResponse> approve(@PathVariable String id, @RequestParam String reviewerId) {
        ProjectApplicationResponse response = applicationService.approveApplication(id, reviewerId);
        return ApiResponse.success(response, "Approved", HttpStatus.OK);
    }

    /**
     * Chức năng: Từ chối đơn ứng tuyển dự án.
     * Service: ProjectApplicationService.rejectApplication() - Cập nhật trạng thái thành REJECTED.
     */
    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('LAB_ADMIN')")
    public ApiResponse<ProjectApplicationResponse> reject(@PathVariable String id, @RequestParam String reviewerId, @RequestParam String reason) {
        ProjectApplicationResponse response = applicationService.rejectApplication(id, reviewerId, reason);
        return ApiResponse.success(response, "Rejected", HttpStatus.OK);
    }

   
}
