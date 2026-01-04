package com.example.labOdc.Controller;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.CompanyRiskRecordDTO;
import com.example.labOdc.DTO.Response.CompanyRiskRecordResponse;
import com.example.labOdc.Service.CompanyRiskRecordService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/company-risk-records")
public class CompanyRiskRecordController {

    private final CompanyRiskRecordService companyRiskRecordService;

    @PostMapping("/")
    public ApiResponse<CompanyRiskRecordResponse> createRiskRecord(
            @Valid @RequestBody CompanyRiskRecordDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ApiResponse.error(errorMessages);
        }

        CompanyRiskRecordResponse response = companyRiskRecordService.createRiskRecord(dto);

        return ApiResponse.success(
                response,
                "Company risk record created successfully",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<CompanyRiskRecordResponse> getById(@PathVariable String id) {
        CompanyRiskRecordResponse response = companyRiskRecordService.getById(id);
        return ApiResponse.success(response, "Risk record retrieved successfully", HttpStatus.OK);
    }

    @GetMapping("/company/{companyId}")
    public ApiResponse<List<CompanyRiskRecordResponse>> getByCompany(@PathVariable String companyId) {
        List<CompanyRiskRecordResponse> list = companyRiskRecordService.getByCompanyId(companyId);
        return ApiResponse.success(list, "Risk records by company retrieved successfully", HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    public ApiResponse<List<CompanyRiskRecordResponse>> getByProject(@PathVariable String projectId) {
        List<CompanyRiskRecordResponse> list = companyRiskRecordService.getByProjectId(projectId);
        return ApiResponse.success(list, "Risk records by project retrieved successfully", HttpStatus.OK);
    }

    @GetMapping("/high-risk")
    public ApiResponse<List<CompanyRiskRecordResponse>> getHighRiskCompanies() {
        List<CompanyRiskRecordResponse> list = companyRiskRecordService.getHighRiskCompanies();
        return ApiResponse.success(list, "High-risk company records retrieved successfully", HttpStatus.OK);
    }
}