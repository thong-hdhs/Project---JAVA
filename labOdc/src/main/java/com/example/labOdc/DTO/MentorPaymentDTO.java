package com.example.labOdc.DTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorPaymentDTO {

    private String fundAllocationId;

    private String mentorId;
    
    private BigDecimal totalAmount;

    private String notes;
}