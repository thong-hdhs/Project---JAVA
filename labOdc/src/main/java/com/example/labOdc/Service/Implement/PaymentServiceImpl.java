package com.example.labOdc.Service.Implement;

import com.example.labOdc.DTO.PaymentDTO;
import com.example.labOdc.DTO.Response.PaymentResponse;
import com.example.labOdc.Model.*;
import com.example.labOdc.Repository.*;
import com.example.labOdc.Service.PaymentService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;

    @Override
    public PaymentResponse createPayment(PaymentDTO dto) {

        
    Project project = null;
    if (dto.getProjectId() != null) {
        project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    Company company = null;
    if (dto.getCompanyId() != null) {
        company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }
        Payment payment = Payment.builder()
                .project(project)
                .company(company)
                .amount(dto.getAmount())
                .paymentType(dto.getPaymentType())
                .status(PaymentStatus.PENDING)
                .dueDate(dto.getDueDate())
                .notes(dto.getNotes())
                .build();

        Payment saved = paymentRepository.save(payment);

        // Demo PayOS (giả lập)
        if (Boolean.TRUE.equals(dto.getUsePayOS())) {

            String fakeCheckoutLink = "https://demo.payos.vn/checkout/"
                    + saved.getId()
                    + "?amount=" + saved.getAmount();

            String qrFileName = "payos-qr-" + saved.getId() + ".png";
            generateQrCodeImage(fakeCheckoutLink, qrFileName);

            saved.setTransactionId(fakeCheckoutLink);
            saved.setNotes("QR generated: /qr/" + qrFileName);

            saved = paymentRepository.save(saved);
        }

        return PaymentResponse.fromEntity(saved);
    }

    @Override
    public PaymentResponse updateStatus(
            String paymentId,
            String statusStr,
            String transactionId,
            LocalDate paymentDate,
            String notes
    ) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        PaymentStatus status = PaymentStatus.valueOf(statusStr.toUpperCase());
        payment.setStatus(status);

        if (transactionId != null) {
            payment.setTransactionId(transactionId);
        }
        if (paymentDate != null) {
            payment.setPaymentDate(paymentDate);
        }
        if (notes != null) {
            payment.setNotes(notes);
        }

        return PaymentResponse.fromEntity(paymentRepository.save(payment));
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getById(String id) {
        return paymentRepository.findById(id)
                .map(PaymentResponse::fromEntity)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getByProjectId(String projectId) {
        return paymentRepository.findByProjectId(projectId)
                .stream()
                .map(PaymentResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getByCompanyId(String companyId) {
        return paymentRepository.findByCompanyId(companyId)
                .stream()
                .map(PaymentResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // Sinh QR code (demo)
    private void generateQrCodeImage(String content, String fileName) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(
                    content,
                    BarcodeFormat.QR_CODE,
                    350,
                    350
            );

            Path qrDir = Paths.get("src/main/resources/static/qr");
            Files.createDirectories(qrDir);

            Path qrPath = qrDir.resolve(fileName);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", qrPath);

        } catch (WriterException | IOException e) {
            throw new RuntimeException("Không thể tạo QR code", e);
        }
    }
}