package com.example.labOdc.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundDistributionDTO {

    @NotBlank(message = "fundAllocationId is required")
    private String fundAllocationId;

    @NotBlank(message = "talentId is required")
    private String talentId;

    @NotNull(message = "amount is required")
    @Positive(message = "amount must be positive")
    private BigDecimal amount;

    @Positive(message = "percentage must be positive")
    private BigDecimal percentage;

    private String notes;
}