package com.example.labOdc.Service;

import com.example.labOdc.DTO.PaymentDTO;
import com.example.labOdc.DTO.Response.PaymentResponse;

import java.time.LocalDate;
import java.util.List;

public interface PaymentService {

    PaymentResponse createPayment(PaymentDTO dto);

    PaymentResponse updateStatus(String paymentId, String status, String transactionId, LocalDate paymentDate, String notes);

    PaymentResponse getById(String id);

    List<PaymentResponse> getByProjectId(String projectId);

    List<PaymentResponse> getByCompanyId(String companyId);
}