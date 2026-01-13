package com.example.labOdc.Service;

import com.example.labOdc.DTO.FundDistributionDTO;
import com.example.labOdc.DTO.Response.FundDistributionResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FundDistributionService {

    FundDistributionResponse createDistribution(FundDistributionDTO dto);

    FundDistributionResponse updateStatus(String distributionId, String status, String approvedById,
                                           LocalDate paidDate, String paymentMethod, String transactionRef, String notes);

    FundDistributionResponse getById(String id);

    List<FundDistributionResponse> getByFundAllocationId(String fundAllocationId);

    List<FundDistributionResponse> getByTalentId(String talentId);

    BigDecimal getTotalPaidForTalent(String talentId);
}