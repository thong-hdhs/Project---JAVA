package com.example.labOdc.DTO.Response;

import com.example.labOdc.Model.LabFundAdvanceStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabFundAdvanceResponse {

    private String id;
    private String projectName;
    private String projectCode;
    private String paymentTransactionId;
    private BigDecimal advanceAmount;
    private String advanceReason;
    private LabFundAdvanceStatus status;
    private String approvedByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}