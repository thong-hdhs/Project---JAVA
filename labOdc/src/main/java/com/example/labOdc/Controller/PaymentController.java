package com.example.labOdc.Controller;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.PaymentDTO;
import com.example.labOdc.DTO.Response.PaymentResponse;
import com.example.labOdc.Service.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'LAB_ADMIN', 'COMPANY')")
    public ApiResponse<PaymentResponse> createPayment(
            @Valid @RequestBody PaymentDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ApiResponse.error(errorMessages);
        }

        PaymentResponse response = paymentService.createPayment(dto);

        return ApiResponse.success(
                response,
                "Payment recorded successfully",
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'LAB_ADMIN')")
    public ApiResponse<PaymentResponse> updatePaymentStatus(
            @PathVariable String id,
            @RequestParam String status,
            @RequestParam(required = false) String transactionId,
            @RequestParam(required = false) LocalDate paymentDate,
            @RequestParam(required = false) String notes) {

        PaymentResponse response = paymentService.updateStatus(id, status, transactionId, paymentDate, notes);

        return ApiResponse.success(
                response,
                "Payment status updated successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'LAB_ADMIN', 'COMPANY')")
    public ApiResponse<PaymentResponse> getById(@PathVariable String id) {
        PaymentResponse response = paymentService.getById(id);
        return ApiResponse.success(response, "Payment retrieved successfully", HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'LAB_ADMIN', 'COMPANY')")
    public ApiResponse<List<PaymentResponse>> getByProject(@PathVariable String projectId) {
        List<PaymentResponse> list = paymentService.getByProjectId(projectId);
        return ApiResponse.success(list, "Payments by project retrieved successfully", HttpStatus.OK);
    }

    @GetMapping("/company/{companyId}")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'LAB_ADMIN', 'COMPANY')")
    public ApiResponse<List<PaymentResponse>> getByCompany(@PathVariable String companyId) {
        List<PaymentResponse> list = paymentService.getByCompanyId(companyId);
        return ApiResponse.success(list, "Payments by company retrieved successfully", HttpStatus.OK);
    }
}