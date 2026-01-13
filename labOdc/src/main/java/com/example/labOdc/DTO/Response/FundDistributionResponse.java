package com.example.labOdc.DTO.Response;

import com.example.labOdc.Model.FundDistributionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundDistributionResponse {

    private String id;
    private String fundAllocationId;
    private String projectName;
    private String talentName;
    private String talentStudentCode;
    private BigDecimal amount;
    private BigDecimal percentage;
    private FundDistributionStatus status;
    private String approvedByName;
    private LocalDateTime approvedAt;
    private LocalDate paidDate;
    private String paymentMethod;
    private String transactionReference;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}