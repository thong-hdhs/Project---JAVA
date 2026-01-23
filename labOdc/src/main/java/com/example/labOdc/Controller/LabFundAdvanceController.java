package com.example.labOdc.Controller;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.LabFundAdvanceDTO;
import com.example.labOdc.DTO.Response.LabFundAdvanceResponse;
import com.example.labOdc.Service.LabFundAdvanceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/lab-fund-advances")
public class LabFundAdvanceController {

    private final LabFundAdvanceService labFundAdvanceService;

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'LAB_ADMIN')")
    public ApiResponse<LabFundAdvanceResponse> createAdvance(
            @Valid @RequestBody LabFundAdvanceDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ApiResponse.error(errorMessages);
        }

        LabFundAdvanceResponse response = labFundAdvanceService.createAdvance(dto);

        return ApiResponse.success(
                response,
                "Lab fund advance created successfully",
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'LAB_ADMIN')")
    public ApiResponse<LabFundAdvanceResponse> updateStatus(
            @PathVariable String id,
            @RequestParam String status,
            @RequestParam(required = false) String approvedById) {

        LabFundAdvanceResponse response = labFundAdvanceService.updateStatus(id, status, approvedById);

        return ApiResponse.success(
                response,
                "Lab fund advance status updated successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'LAB_ADMIN')")
    public ApiResponse<LabFundAdvanceResponse> getById(@PathVariable String id) {
        LabFundAdvanceResponse response = labFundAdvanceService.getById(id);
        return ApiResponse.success(response, "Lab fund advance retrieved successfully", HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'LAB_ADMIN')")
    public ApiResponse<List<LabFundAdvanceResponse>> getByProject(@PathVariable String projectId) {
        List<LabFundAdvanceResponse> list = labFundAdvanceService.getByProjectId(projectId);
        return ApiResponse.success(list, "Lab fund advances by project retrieved successfully", HttpStatus.OK);
    }
}