package com.example.labOdc.DTO.Response;

import com.example.labOdc.Model.MentorPaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorPaymentResponse {

    private String id;
    private String projectName;
    private String projectCode;
    private String mentorName;
    private String mentorEmail;
    private BigDecimal amount;
    private MentorPaymentStatus status;
    private String approvedByName;
    private LocalDateTime approvedAt;
    private LocalDate paidDate;
    private String paymentMethod;
    private String transactionReference;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}