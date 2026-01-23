package com.example.labOdc.DTO;

import com.example.labOdc.Model.PaymentMethod;
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

    @NotBlank(message = "projectId is required")
    private String projectId;

    @NotBlank(message = "companyId is required")
    private String companyId;

    @NotNull(message = "amount is required")
    @Positive(message = "amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "paymentType is required")
    private PaymentType paymentType;

    private LocalDate dueDate;

    private PaymentMethod paymentMethod;

    private String invoiceNumber;

    private String notes;
}