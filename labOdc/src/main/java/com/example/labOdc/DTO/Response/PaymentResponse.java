package com.example.labOdc.DTO.Response;

import com.example.labOdc.Model.PaymentMethod;
import com.example.labOdc.Model.PaymentStatus;
import com.example.labOdc.Model.PaymentType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private String id;
    private String projectName;
    private String projectCode;
    private String companyName;
    private BigDecimal amount;
    private PaymentType paymentType;
    private PaymentStatus status;
    private String transactionId;
    private LocalDate paymentDate;
    private LocalDate dueDate;
    private String paymentGateway;
    private PaymentMethod paymentMethod;
    private String invoiceNumber;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}