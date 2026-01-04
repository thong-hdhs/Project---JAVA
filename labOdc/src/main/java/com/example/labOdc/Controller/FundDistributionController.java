package com.example.labOdc.Controller;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.FundDistributionDTO;
import com.example.labOdc.DTO.Response.FundDistributionResponse;
import com.example.labOdc.Service.FundDistributionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/fund-distributions")
public class FundDistributionController {

    private final FundDistributionService fundDistributionService;

    @PostMapping("/")
    public ApiResponse<FundDistributionResponse> createDistribution(
            @Valid @RequestBody FundDistributionDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ApiResponse.error(errorMessages);
        }

        FundDistributionResponse response = fundDistributionService.createDistribution(dto);

        return ApiResponse.success(
                response,
                "Fund distribution to talent created successfully",
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<FundDistributionResponse> updateDistributionStatus(
            @PathVariable String id,
            @RequestParam String status,
            @RequestParam(required = false) String approvedById,
            @RequestParam(required = false) LocalDate paidDate,
            @RequestParam(required = false) String paymentMethod,
            @RequestParam(required = false) String transactionRef,
            @RequestParam(required = false) String notes) {

        FundDistributionResponse response = fundDistributionService.updateStatus(id, status, approvedById, paidDate, paymentMethod, transactionRef, notes);

        return ApiResponse.success(
                response,
                "Fund distribution status updated successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<FundDistributionResponse> getById(@PathVariable String id) {
        FundDistributionResponse response = fundDistributionService.getById(id);
        return ApiResponse.success(response, "Fund distribution retrieved successfully", HttpStatus.OK);
    }

    @GetMapping("/allocation/{fundAllocationId}")
    public ApiResponse<List<FundDistributionResponse>> getByAllocation(@PathVariable String fundAllocationId) {
        List<FundDistributionResponse> list = fundDistributionService.getByFundAllocationId(fundAllocationId);
        return ApiResponse.success(list, "Distributions by allocation retrieved successfully", HttpStatus.OK);
    }

    @GetMapping("/talent/{talentId}")
    public ApiResponse<List<FundDistributionResponse>> getByTalent(@PathVariable String talentId) {
        List<FundDistributionResponse> list = fundDistributionService.getByTalentId(talentId);
        return ApiResponse.success(list, "Distributions by talent retrieved successfully", HttpStatus.OK);
    }

    @GetMapping("/talent/{talentId}/total-paid")
    public ApiResponse<BigDecimal> getTotalPaidForTalent(@PathVariable String talentId) {
        BigDecimal total = fundDistributionService.getTotalPaidForTalent(talentId);
        return ApiResponse.success(total, "Total paid to talent retrieved successfully", HttpStatus.OK);
    }
}