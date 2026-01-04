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
public class MentorPaymentDTO {

    @NotBlank(message = "mentorPayment.fundAllocationId.required")
    private String fundAllocationId;

    @NotBlank(message = "mentorPayment.mentorId.required")
    private String mentorId;

    @NotBlank(message = "mentorPayment.projectId.required")
    private String projectId;

    @NotNull(message = "mentorPayment.amount.required")
    @Positive(message = "mentorPayment.amount.mustBePositive")
    private BigDecimal amount;

    private String notes;
}