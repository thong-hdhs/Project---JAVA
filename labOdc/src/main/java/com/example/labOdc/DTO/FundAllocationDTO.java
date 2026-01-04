package com.example.labOdc.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundAllocationDTO {

    @NotBlank(message = "paymentId is required")
    private String paymentId;

    @NotBlank(message = "projectId is required")
    private String projectId;

    @Positive(message = "totalAmount must be positive")
    private BigDecimal totalAmount;

    private String notes;
}