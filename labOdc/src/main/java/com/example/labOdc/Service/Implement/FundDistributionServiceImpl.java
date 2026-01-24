package com.example.labOdc.Service.Implement;

import com.example.labOdc.DTO.FundDistributionDTO;
import com.example.labOdc.DTO.Response.FundDistributionResponse;
import com.example.labOdc.Model.*;
import com.example.labOdc.Repository.*;
import com.example.labOdc.Service.FundDistributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FundDistributionServiceImpl implements FundDistributionService {

        private final FundDistributionRepository fundDistributionRepository;
        private final FundAllocationRepository fundAllocationRepository;
        private final TalentRepository talentRepository;
        private final UserRepository userRepository;

        // ========== CREATE ==========
        @Override
        public FundDistributionResponse createDistribution(FundDistributionDTO dto) {

                FundAllocation allocation = dto.getFundAllocationId() != null
                                ? fundAllocationRepository.findById(dto.getFundAllocationId()).orElse(null)
                                : null;

                Talent talent = dto.getTalentId() != null
                                ? talentRepository.findById(dto.getTalentId()).orElse(null)
                                : null;

                FundDistribution distribution = FundDistribution.builder()
                                .fundAllocation(allocation)
                                .talent(talent)
                                .amount(dto.getAmount())
                                .percentage(dto.getPercentage())
                                .status(FundDistributionStatus.PENDING)
                                .notes(dto.getNotes())
                                .build();

                return FundDistributionResponse.fromEntity(
                                fundDistributionRepository.save(distribution));
        }

        // ========== UPDATE STATUS ==========
        @Override
        public FundDistributionResponse updateStatus(
                        String distributionId,
                        String statusStr,
                        String approvedById,
                        LocalDate paidDate,
                        String paymentMethod,
                        String transactionRef,
                        String notes) {

                FundDistribution distribution = fundDistributionRepository.findById(distributionId)
                                .orElseThrow(() -> new RuntimeException("Fund distribution not found"));

                distribution.setStatus(FundDistributionStatus.valueOf(statusStr.toUpperCase()));

                if (approvedById != null) {
                        User approvedBy = userRepository.findById(approvedById)
                                        .orElseThrow(() -> new RuntimeException("User not found"));
                        distribution.setApprovedBy(approvedBy);
                        distribution.setApprovedAt(LocalDateTime.now());
                }

                if (paidDate != null)
                        distribution.setPaidDate(paidDate);
                if (paymentMethod != null)
                        distribution.setPaymentMethod(paymentMethod);
                if (transactionRef != null)
                        distribution.setTransactionReference(transactionRef);
                if (notes != null)
                        distribution.setNotes(notes);

                return FundDistributionResponse.fromEntity(
                                fundDistributionRepository.save(distribution));
        }

        // ========== GET BY ID ==========
        @Override
        @Transactional(readOnly = true)
        public FundDistributionResponse getById(String id) {
                return fundDistributionRepository.findById(id)
                                .map(FundDistributionResponse::fromEntity)
                                .orElseThrow(() -> new RuntimeException("Fund distribution not found"));
        }

        // ========== GET BY FUND ALLOCATION ==========
        @Override
        @Transactional(readOnly = true)
        public List<FundDistributionResponse> getByFundAllocationId(String fundAllocationId) {
                return fundDistributionRepository.findByFundAllocationId(fundAllocationId)
                                .stream()
                                .map(FundDistributionResponse::fromEntity)
                                .toList();
        }

        // ========== GET BY TALENT ==========
        @Override
        @Transactional(readOnly = true)
        public List<FundDistributionResponse> getByTalentId(String talentId) {
                return fundDistributionRepository.findByTalentId(talentId)
                                .stream()
                                .map(FundDistributionResponse::fromEntity)
                                .toList();
        }

        // ========== GET BY STATUS ==========
        @Override
        @Transactional(readOnly = true)
        public List<FundDistributionResponse> getByStatus(String status) {
                return fundDistributionRepository
                                .findByStatus(FundDistributionStatus.valueOf(status.toUpperCase()))
                                .stream()
                                .map(FundDistributionResponse::fromEntity)
                                .toList();
        }

        // ========== TOTAL PAID ==========
        @Override
        @Transactional(readOnly = true)
        public BigDecimal getTotalPaidForTalent(String talentId) {
                return fundDistributionRepository.findByTalentId(talentId)
                                .stream()
                                .filter(d -> d.getStatus() == FundDistributionStatus.PAID)
                                .map(FundDistribution::getAmount)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
}