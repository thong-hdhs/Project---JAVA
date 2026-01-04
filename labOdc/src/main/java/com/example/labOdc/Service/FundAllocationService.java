package com.example.labOdc.Service;

import com.example.labOdc.DTO.FundAllocationDTO;
import com.example.labOdc.DTO.Response.FundAllocationResponse;

public interface FundAllocationService {

    FundAllocationResponse allocateFund(FundAllocationDTO dto);

    FundAllocationResponse getById(String id);

    FundAllocationResponse getByPaymentId(String paymentId);

    FundAllocationResponse updateStatus(String id, String status, String allocatedById, String notes);
}