package com.example.labOdc.DTO;

<<<<<<< HEAD
import jakarta.validation.constraints.NotBlank;

import lombok.*;
=======
import java.math.BigDecimal;
>>>>>>> feature/big-update

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorPaymentDTO {

    private String fundAllocationId;

<<<<<<< HEAD
=======
    private String mentorId;
    
    private BigDecimal totalAmount;

>>>>>>> feature/big-update
    private String notes;
}