package com.example.labOdc.Controller;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.FundAllocationDTO;
import com.example.labOdc.DTO.Response.FundAllocationResponse;
import com.example.labOdc.Service.FundAllocationService;
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
@RequestMapping("/api/v1/fund-allocations")
public class FundAllocationController {

    private final FundAllocationService fundAllocationService;

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'LAB_ADMIN')")
    public ApiResponse<FundAllocationResponse> allocateFund(
            @Valid @RequestBody FundAllocationDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ApiResponse.error(errorMessages);
        }

        FundAllocationResponse response = fundAllocationService.allocateFund(dto);

        return ApiResponse.success(
                response,
                "Fund allocated successfully (70% team / 20% mentor / 10% lab)",
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'LAB_ADMIN')")
    public ApiResponse<FundAllocationResponse> updateAllocationStatus(
            @PathVariable String id,
            @RequestParam String status,
            @RequestParam(required = false) String allocatedById,
            @RequestParam(required = false) String notes) {

        FundAllocationResponse response = fundAllocationService.updateStatus(id, status, allocatedById, notes);

        return ApiResponse.success(
                response,
                "Fund allocation status updated successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'LAB_ADMIN')")
    public ApiResponse<FundAllocationResponse> getById(@PathVariable String id) {
        FundAllocationResponse response = fundAllocationService.getById(id);
        return ApiResponse.success(response, "Fund allocation retrieved successfully", HttpStatus.OK);
    }

    @GetMapping("/payment/{paymentId}")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'LAB_ADMIN')")
    public ApiResponse<FundAllocationResponse> getByPaymentId(@PathVariable String paymentId) {
        FundAllocationResponse response = fundAllocationService.getByPaymentId(paymentId);
        return ApiResponse.success(response, "Fund allocation by payment retrieved successfully", HttpStatus.OK);
    }
}