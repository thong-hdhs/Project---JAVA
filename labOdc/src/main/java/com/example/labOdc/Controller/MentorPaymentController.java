package com.example.labOdc.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.MentorPaymentDTO;
import com.example.labOdc.DTO.Response.MentorPaymentResponse;
import com.example.labOdc.Model.MentorPaymentStatus;
import com.example.labOdc.Service.MentorPaymentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/mentor-payments")
public class MentorPaymentController {

    private final MentorPaymentService mentorPaymentService;

    @PostMapping("/")
    public ApiResponse<MentorPaymentResponse> createMentorPayment(
            @Valid @RequestBody MentorPaymentDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ApiResponse.error(errorMessages);
        }

        MentorPaymentResponse response = mentorPaymentService.createFromAllocation(dto);

        return ApiResponse.success(
                response,
                "Mentor payment created successfully",
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<MentorPaymentResponse> updatePaymentStatus(
            @PathVariable String id,
            @RequestParam MentorPaymentStatus status,
            @RequestParam(required = false) String approvedById,
            @RequestParam(required = false) String notes) {

        MentorPaymentResponse response = mentorPaymentService.updateStatus(id, status, approvedById, notes);

        return ApiResponse.success(
                response,
                "Mentor payment status updated successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<MentorPaymentResponse> getMentorPaymentById(@PathVariable String id) {
        MentorPaymentResponse response = mentorPaymentService.getById(id);

        return ApiResponse.success(
                response,
                "Mentor payment retrieved successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/mentor/{mentorId}")
    public ApiResponse<List<MentorPaymentResponse>> getPaymentsByMentor(@PathVariable String mentorId) {
        List<MentorPaymentResponse> list = mentorPaymentService.getByMentorId(mentorId);

        return ApiResponse.success(
                list,
                "Mentor payments retrieved successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/project/{projectId}")
    public ApiResponse<List<MentorPaymentResponse>> getPaymentsByProject(@PathVariable String projectId) {
        List<MentorPaymentResponse> list = mentorPaymentService.getByProjectId(projectId);

        return ApiResponse.success(
                list,
                "Project mentor payments retrieved successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/mentor/{mentorId}/total-paid")
    public ApiResponse<BigDecimal> getTotalPaidForMentor(@PathVariable String mentorId) {
        BigDecimal total = mentorPaymentService.getTotalPaidForMentor(mentorId);

        return ApiResponse.success(
                total,
                "Total paid amount retrieved successfully",
                HttpStatus.OK
        );
    }
}