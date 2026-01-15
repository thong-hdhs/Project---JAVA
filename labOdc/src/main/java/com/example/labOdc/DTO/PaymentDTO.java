package com.example.labOdc.DTO;

import com.example.labOdc.Model.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {

    @NotBlank
    private String projectId;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private PaymentType paymentType;

    private LocalDate dueDate;

    private String notes;
}