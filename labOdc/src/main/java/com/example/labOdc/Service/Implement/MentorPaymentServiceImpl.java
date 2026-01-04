package com.example.labOdc.Service.Implement;

import com.example.labOdc.DTO.MentorPaymentDTO;
import com.example.labOdc.DTO.Response.MentorPaymentResponse;
import com.example.labOdc.Model.*;
import com.example.labOdc.Repository.*;
import com.example.labOdc.Service.MentorPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MentorPaymentServiceImpl implements MentorPaymentService {

    private final MentorPaymentRepository mentorPaymentRepository;
    private final FundAllocationRepository fundAllocationRepository;
    private final MentorRepository mentorRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public MentorPaymentResponse createFromAllocation(MentorPaymentDTO dto) {

        FundAllocation allocation = fundAllocationRepository.findById(dto.getFundAllocationId())
                .orElseThrow(() -> new RuntimeException("Fund allocation not found"));

        Mentor mentor = mentorRepository.findById(dto.getMentorId())
                .orElseThrow(() -> new RuntimeException("Mentor not found"));

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        MentorPayment payment = MentorPayment.builder()
                .fundAllocation(allocation)
                .mentor(mentor)
                .project(project)
                .amount(dto.getAmount()) // Usually 20% from fund_allocations.mentor_amount
                .status(MentorPaymentStatus.PENDING)
                .notes(dto.getNotes())
                .build();

        MentorPayment saved = mentorPaymentRepository.save(payment);
        return mapToResponse(saved);
    }

    @Override
    public MentorPaymentResponse updateStatus(
            String paymentId,
            MentorPaymentStatus newStatus,
            String approvedById,
            String notes) {

        MentorPayment payment = mentorPaymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Mentor payment not found"));

        payment.setStatus(newStatus);

        if (approvedById != null &&
                (newStatus == MentorPaymentStatus.APPROVED || newStatus == MentorPaymentStatus.PAID)) {

            User approvedBy = userRepository.findById(approvedById)
                    .orElseThrow(() -> new RuntimeException("Approver not found"));

            payment.setApprovedBy(approvedBy);
            payment.setApprovedAt(LocalDateTime.now());
        }

        if (notes != null) {
            payment.setNotes(notes);
        }

        MentorPayment updated = mentorPaymentRepository.save(payment);
        return mapToResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public MentorPaymentResponse getById(String id) {
        return mentorPaymentRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Mentor payment not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorPaymentResponse> getByMentorId(String mentorId) {
        return mentorPaymentRepository.findByMentorId(mentorId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorPaymentResponse> getByProjectId(String projectId) {
        return mentorPaymentRepository.findByProjectId(projectId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalPaidForMentor(String mentorId) {
        return mentorPaymentRepository.getTotalPaidAmountByMentor(mentorId);
    }

    private MentorPaymentResponse mapToResponse(MentorPayment payment) {
        return MentorPaymentResponse.builder()
                .id(payment.getId())
                .projectName(payment.getProject().getProjectName())
                .projectCode(payment.getProject().getProjectCode())
                .mentorName(payment.getMentor().getUser().getFullName())
                .mentorEmail(payment.getMentor().getUser().getEmail())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .approvedByName(
                        payment.getApprovedBy() != null
                                ? payment.getApprovedBy().getFullName()
                                : null
                )
                .approvedAt(payment.getApprovedAt())
                .paidDate(payment.getPaidDate())
                .paymentMethod(payment.getPaymentMethod())
                .transactionReference(payment.getTransactionReference())
                .notes(payment.getNotes())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}