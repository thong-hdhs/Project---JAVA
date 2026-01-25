package com.example.labOdc.Controller;

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
import com.example.labOdc.DTO.LabAdminDTO;
import com.example.labOdc.DTO.Response.LabAdminResponse;
import com.example.labOdc.Service.LabAdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/lab-admins")
public class LabAdminController {
    private final LabAdminService labAdminService;

    public LabAdminController(LabAdminService labAdminService) {
        this.labAdminService = labAdminService;
    }

    /**
     * Chức năng: Tạo tài khoản Lab Administrator mới.
     * Service: LabAdminService.createLabAdmin() - Xử lý logic tạo và lưu entity.
     */
    @PostMapping("/")
@PreAuthorize("hasRole('SYSTEM_ADMIN')")
public ApiResponse<LabAdminResponse> createLabAdmin(
        @Valid @RequestBody LabAdminDTO dto,
        BindingResult result) {

    if (result.hasErrors()) {
        List<String> errors = result.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return ApiResponse.error(errors);
    }

    LabAdminResponse response = labAdminService.createLabAdmin(dto);
    return ApiResponse.success(response, "Created", HttpStatus.CREATED);
}

    /**
     * Chức năng: Lấy danh sách tất cả Lab Administrators.
     * Service: LabAdminService.getAllLabAdmins() - Truy vấn và trả về list.
     */
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'LAB_ADMIN')")
    public ApiResponse<List<LabAdminResponse>> getAll() {
        List<LabAdminResponse> list = labAdminService.getAllLabAdmins();
        return ApiResponse.success(list, "OK", HttpStatus.OK);
    }

    /**
     * Chức năng: Lấy Lab Administrator theo ID.
     * Service: LabAdminService.getLabAdminById() - Truy vấn entity theo ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'LAB_ADMIN')")
    public ApiResponse<LabAdminResponse> getById(@PathVariable String id) {
        LabAdminResponse response = labAdminService.getLabAdminById(id);
        return ApiResponse.success(response, "OK", HttpStatus.OK);
    }

    /**
     * Chức năng: Cập nhật Lab Administrator theo ID.
     * Service: LabAdminService.updateLabAdmin() - Xử lý cập nhật entity.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'LAB_ADMIN')")
    public ApiResponse<LabAdminResponse> update(@Valid @RequestBody LabAdminDTO dto, @PathVariable String id) {
        LabAdminResponse response = labAdminService.updateLabAdmin(dto, id);
        return ApiResponse.success(response, "Updated", HttpStatus.OK);
    }

    /**
     * Chức năng: Xóa Lab Administrator theo ID.
     * Service: LabAdminService.deleteLabAdmin() - Xử lý xóa entity.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable String id) {
        labAdminService.deleteLabAdmin(id);
        return ResponseEntity.ok("Deleted");
    }

}
