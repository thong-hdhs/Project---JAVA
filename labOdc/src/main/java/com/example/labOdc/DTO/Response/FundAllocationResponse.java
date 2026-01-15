package com.example.labOdc.DTO.Response;

import com.example.labOdc.Model.FundAllocationStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundAllocationResponse {

    private String id;
    private String paymentId;
    private String projectName;
    private String projectCode;
    private BigDecimal totalAmount;
    private BigDecimal teamAmount;
    private BigDecimal mentorAmount;
    private BigDecimal labAmount;
    private BigDecimal teamPercentage;
    private BigDecimal mentorPercentage;
    private BigDecimal labPercentage;
    private FundAllocationStatus status;
    private String allocatedByName;
    private LocalDateTime allocatedAt;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}