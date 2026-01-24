package com.example.labOdc.DTO;

<<<<<<< HEAD
=======
import com.example.labOdc.Model.PaymentStatus;
>>>>>>> feature/big-update
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

<<<<<<< HEAD
    @NotBlank
    private String projectId;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
=======
    private String projectId;

    private String companyId;

    @NotNull(message = "amount is required")
    @Positive(message = "amount must be greater than 0")
    private BigDecimal amount;

>>>>>>> feature/big-update
    private PaymentType paymentType;

    private String transactionId;

    private LocalDate dueDate;

    private String notes;

    @Builder.Default
    private Boolean usePayOS = true;
}