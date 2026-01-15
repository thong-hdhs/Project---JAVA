package com.example.labOdc.DTO;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorPaymentDTO {

    @NotBlank(message = "mentorPayment.fundAllocationId.required")
    private String fundAllocationId;

    private String notes;
}