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

    @Override
    public FundDistributionResponse createDistribution(FundDistributionDTO dto) {
        FundAllocation allocation = fundAllocationRepository.findById(dto.getFundAllocationId())
                .orElseThrow(() -> new RuntimeException("Fund allocation not found"));

        Talent talent = talentRepository.findById(dto.getTalentId())
                .orElseThrow(() -> new RuntimeException("Talent not found"));

        FundDistribution distribution = FundDistribution.builder()
                .fundAllocation(allocation)
                .talent(talent)
                .amount(dto.getAmount())
                .percentage(dto.getPercentage())
                .status(FundDistributionStatus.PENDING)
                .notes(dto.getNotes())
                .build();

        FundDistribution saved = fundDistributionRepository.save(distribution);
        return mapToResponse(saved);
    }

    @Override
    public FundDistributionResponse updateStatus(String distributionId, String statusStr, String approvedById,
                                                 LocalDate paidDate, String paymentMethod, String transactionRef, String notes) {
        FundDistribution distribution = fundDistributionRepository.findById(distributionId)
                .orElseThrow(() -> new RuntimeException("Fund distribution not found"));

        FundDistributionStatus status = FundDistributionStatus.valueOf(statusStr.toUpperCase());
        distribution.setStatus(status);

        if (approvedById != null) {
            User approvedBy = userRepository.findById(approvedById)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            distribution.setApprovedBy(approvedBy);
            distribution.setApprovedAt(LocalDateTime.now());
        }

        if (paidDate != null) distribution.setPaidDate(paidDate);
        if (paymentMethod != null) distribution.setPaymentMethod(paymentMethod);
        if (transactionRef != null) distribution.setTransactionReference(transactionRef);
        if (notes != null) distribution.setNotes(notes);

        FundDistribution updated = fundDistributionRepository.save(distribution);
        return mapToResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public FundDistributionResponse getById(String id) {
        FundDistribution distribution = fundDistributionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fund distribution not found"));
        return mapToResponse(distribution);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FundDistributionResponse> getByFundAllocationId(String fundAllocationId) {
        return fundDistributionRepository.findByFundAllocationId(fundAllocationId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FundDistributionResponse> getByTalentId(String talentId) {
        return fundDistributionRepository.findByTalentId(talentId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalPaidForTalent(String talentId) {
        return fundDistributionRepository.findByTalentId(talentId)
                .stream()
                .filter(d -> d.getStatus() == FundDistributionStatus.PAID)
                .map(FundDistribution::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private FundDistributionResponse mapToResponse(FundDistribution distribution) {
        return FundDistributionResponse.builder()
                .id(distribution.getId())
                .fundAllocationId(distribution.getFundAllocation().getId())
                .projectName(distribution.getFundAllocation().getProject().getProjectName())
                .talentName(distribution.getTalent().getUser().getFullName())
                .talentStudentCode(distribution.getTalent().getStudentCode())
                .amount(distribution.getAmount())
                .percentage(distribution.getPercentage())
                .status(distribution.getStatus())
                .approvedByName(distribution.getApprovedBy() != null ? distribution.getApprovedBy().getFullName() : null)
                .approvedAt(distribution.getApprovedAt())
                .paidDate(distribution.getPaidDate())
                .paymentMethod(distribution.getPaymentMethod())
                .transactionReference(distribution.getTransactionReference())
                .notes(distribution.getNotes())
                .createdAt(distribution.getCreatedAt())
                .updatedAt(distribution.getUpdatedAt())
                .build();
    }
}