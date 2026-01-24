package com.example.labOdc.Service.Implement;

import com.example.labOdc.DTO.FundAllocationDTO;
import com.example.labOdc.DTO.Response.FundAllocationResponse;
import com.example.labOdc.Model.*;
import com.example.labOdc.Repository.*;
import com.example.labOdc.Service.FundAllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class FundAllocationServiceImpl implements FundAllocationService {

    private final FundAllocationRepository fundAllocationRepository;
    private final PaymentRepository paymentRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public FundAllocationResponse allocateFund(FundAllocationDTO dto) {

        Payment payment = null;
    if (dto.getPaymentId() != null) {
        payment = paymentRepository.findById(dto.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (!payment.getStatus().equals(PaymentStatus.COMPLETED)) {
            throw new RuntimeException("Payment must be COMPLETED to allocate fund");
        }
    }

    Project project = null;
    if (dto.getProjectId() != null) {
        project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

        BigDecimal total = dto.getTotalAmount() != null ? dto.getTotalAmount() : payment.getAmount();

        BigDecimal teamAmt = total.multiply(new BigDecimal("0.70")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal mentorAmt = total.multiply(new BigDecimal("0.20")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal labAmt = total.multiply(new BigDecimal("0.10")).setScale(2, RoundingMode.HALF_UP);

        FundAllocation allocation = FundAllocation.builder()
                .payment(payment)
                .project(project)
                .totalAmount(total)
                .teamAmount(teamAmt)
                .mentorAmount(mentorAmt)
                .labAmount(labAmt)
                .status(FundAllocationStatus.ALLOCATED)
                .notes(dto.getNotes())
                .build();

        FundAllocation saved = fundAllocationRepository.save(allocation);
        return mapToResponse(saved);
    }

    @Override
    public FundAllocationResponse updateStatus(String id, String statusStr, String allocatedById, String notes) {
        FundAllocation allocation = fundAllocationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fund allocation not found"));

        FundAllocationStatus status = FundAllocationStatus.valueOf(statusStr.toUpperCase());
        allocation.setStatus(status);

        if (allocatedById != null) {
            User user = userRepository.findById(allocatedById)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            allocation.setAllocatedBy(user);
            allocation.setAllocatedAt(LocalDateTime.now());
        }

        if (notes != null) {
            allocation.setNotes(notes);
        }

        FundAllocation updated = fundAllocationRepository.save(allocation);
        return mapToResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public FundAllocationResponse getById(String id) {
        FundAllocation allocation = fundAllocationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fund allocation not found"));
        return mapToResponse(allocation);
    }

    @Override
    @Transactional(readOnly = true)
    public FundAllocationResponse getByPaymentId(String paymentId) {
        FundAllocation allocation = fundAllocationRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Fund allocation not found for payment"));
        return mapToResponse(allocation);
    }

    private FundAllocationResponse mapToResponse(FundAllocation allocation) {
        return FundAllocationResponse.builder()
                .id(allocation.getId())
                .paymentId(allocation.getPayment() != null ? allocation.getPayment().getId() : null)
                .projectName(allocation.getProject() != null ? allocation.getProject().getProjectName() : null)
                .projectCode(allocation.getProject() != null ? allocation.getProject().getProjectCode() : null)
                .totalAmount(allocation.getTotalAmount())
                .teamAmount(allocation.getTeamAmount())
                .mentorAmount(allocation.getMentorAmount())
                .labAmount(allocation.getLabAmount())
                .teamPercentage(allocation.getTeamPercentage())
                .mentorPercentage(allocation.getMentorPercentage())
                .labPercentage(allocation.getLabPercentage())
                .status(
                    allocation.getStatus() != null
                        ? allocation.getStatus().name()
                        : null
                )
                .allocatedByName(allocation.getAllocatedBy() != null ? allocation.getAllocatedBy().getFullName() : null)
                .allocatedAt(allocation.getAllocatedAt())
                .notes(allocation.getNotes())
                .createdAt(allocation.getCreatedAt())
                .updatedAt(allocation.getUpdatedAt())
                .build();
    }
}