//package com.example.labOdc.Service.Implement;
//
//import com.example.labOdc.DTO.PaymentDTO;
//import com.example.labOdc.DTO.Response.PaymentResponse;
//import com.example.labOdc.Model.*;
//import com.example.labOdc.Repository.*;
//import com.example.labOdc.Service.PaymentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class PaymentServiceImpl implements PaymentService {
//
//    private final PaymentRepository paymentRepository;
//    private final ProjectRepository projectRepository;
//    private final CompanyRepository companyRepository;
//
//    @Override
//    public PaymentResponse createPayment(PaymentDTO dto) {
//        Project project = projectRepository.findById(dto.getProjectId())
//                .orElseThrow(() -> new RuntimeException("Project not found"));
//
//        Company company = companyRepository.findById(dto.getCompanyId())
//                .orElseThrow(() -> new RuntimeException("Company not found"));
//
//        Payment payment = Payment.builder()
//                .project(project)
//                .company(company)
//                .amount(dto.getAmount())
//                .paymentType(dto.getPaymentType())
//                .status(PaymentStatus.PENDING)
//                .dueDate(dto.getDueDate())
//                .paymentMethod(dto.getPaymentMethod())
//                .invoiceNumber(dto.getInvoiceNumber())
//                .notes(dto.getNotes())
//                .build();
//
//        Payment saved = paymentRepository.save(payment);
//        return mapToResponse(saved);
//    }
//
//    @Override
//    public PaymentResponse updateStatus(String paymentId, String statusStr, String transactionId,
//                                        LocalDate paymentDate, String notes) {
//        Payment payment = paymentRepository.findById(paymentId)
//                .orElseThrow(() -> new RuntimeException("Payment not found"));
//
//        PaymentStatus status = PaymentStatus.valueOf(statusStr.toUpperCase());
//        payment.setStatus(status);
//
//        if (transactionId != null) {
//            payment.setTransactionId(transactionId);
//        }
//        if (paymentDate != null) {
//            payment.setPaymentDate(paymentDate);
//        }
//        if (notes != null) {
//            payment.setNotes(notes);
//        }
//
//        Payment updated = paymentRepository.save(payment);
//        return mapToResponse(updated);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public PaymentResponse getById(String id) {
//        Payment payment = paymentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Payment not found"));
//        return mapToResponse(payment);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<PaymentResponse> getByProjectId(String projectId) {
//        return paymentRepository.findByProjectId(projectId)
//                .stream()
//                .map(this::mapToResponse)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<PaymentResponse> getByCompanyId(String companyId) {
//        return paymentRepository.findByCompanyId(companyId)
//                .stream()
//                .map(this::mapToResponse)
//                .collect(Collectors.toList());
//    }
//
//    private PaymentResponse mapToResponse(Payment payment) {
//        return PaymentResponse.builder()
//                .id(payment.getId())
//                .projectName(payment.getProject().getProjectName())
//                .projectCode(payment.getProject().getProjectCode())
//                .companyName(payment.getCompany().getCompanyName())
//                .amount(payment.getAmount())
//                .paymentType(payment.getPaymentType())
//                .status(payment.getStatus())
//                .transactionId(payment.getTransactionId())
//                .paymentDate(payment.getPaymentDate())
//                .dueDate(payment.getDueDate())
//                .paymentGateway(payment.getPaymentGateway())
//                .paymentMethod(payment.getPaymentMethod())
//                .invoiceNumber(payment.getInvoiceNumber())
//                .notes(payment.getNotes())
//                .createdAt(payment.getCreatedAt())
//                .updatedAt(payment.getUpdatedAt())
//                .build();
//    }
//}