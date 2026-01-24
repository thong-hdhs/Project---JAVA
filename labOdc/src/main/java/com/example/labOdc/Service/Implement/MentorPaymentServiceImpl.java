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
        private final UserRepository userRepository;

        @Override
        public MentorPaymentResponse createFromAllocation(MentorPaymentDTO dto) {

                FundAllocation allocation = null;
                if (dto.getFundAllocationId() != null) {
                        allocation = fundAllocationRepository
                                        .findById(dto.getFundAllocationId())
                                        .orElse(null); // ❗ KHÔNG throw
                }

                // ❗ mentorAmount lấy từ fund allocation (20%)
                MentorPayment payment = MentorPayment.builder()
                                .fundAllocation(allocation)
                                .mentor(allocation != null && allocation.getProject() != null
                                                ? allocation.getProject().getMentor()
                                                : null)
                                .project(allocation != null ? allocation.getProject() : null)
                                .amount(dto.getTotalAmount())
                                .status(MentorPaymentStatus.PENDING)
                                .notes(dto.getNotes())
                                .build();

                MentorPayment saved = mentorPaymentRepository.save(payment);
                return MentorPaymentResponse.fromEntity(saved);
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
                                (newStatus == MentorPaymentStatus.APPROVED
                                                || newStatus == MentorPaymentStatus.PAID)) {

                        User approver = userRepository.findById(approvedById)
                                        .orElseThrow(() -> new RuntimeException("Approver not found"));

                        payment.setApprovedBy(approver);
                        payment.setApprovedAt(LocalDateTime.now());
                }

                if (notes != null) {
                        payment.setNotes(notes);
                }

                MentorPayment updated = mentorPaymentRepository.save(payment);
                return MentorPaymentResponse.fromEntity(updated);
        }

        @Override
        @Transactional(readOnly = true)
        public MentorPaymentResponse getById(String id) {
                return mentorPaymentRepository.findById(id)
                                .map(MentorPaymentResponse::fromEntity)
                                .orElseThrow(() -> new RuntimeException("Mentor payment not found"));
        }

        @Override
        @Transactional(readOnly = true)
        public List<MentorPaymentResponse> getByMentorId(String mentorId) {
                return mentorPaymentRepository.findByMentorId(mentorId)
                                .stream()
                                .map(MentorPaymentResponse::fromEntity)
                                .collect(Collectors.toList());
        }

        @Override
        @Transactional(readOnly = true)
        public List<MentorPaymentResponse> getByProjectId(String projectId) {
                return mentorPaymentRepository.findByProjectId(projectId)
                                .stream()
                                .map(MentorPaymentResponse::fromEntity)
                                .collect(Collectors.toList());
        }

        @Override
        @Transactional(readOnly = true)
        public BigDecimal getTotalPaidForMentor(String mentorId) {
                return mentorPaymentRepository
                                .sumAmountByMentor_IdAndStatus(
                                                mentorId,
                                                MentorPaymentStatus.PAID);
        }
}