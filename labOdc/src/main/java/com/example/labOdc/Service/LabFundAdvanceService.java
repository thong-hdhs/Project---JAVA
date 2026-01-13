package com.example.labOdc.Service;

import com.example.labOdc.DTO.LabFundAdvanceDTO;
import com.example.labOdc.DTO.Response.LabFundAdvanceResponse;

import java.util.List;

public interface LabFundAdvanceService {

    LabFundAdvanceResponse createAdvance(LabFundAdvanceDTO dto);

    LabFundAdvanceResponse updateStatus(String advanceId, String status, String approvedById);

    LabFundAdvanceResponse getById(String id);

    List<LabFundAdvanceResponse> getByProjectId(String projectId);
}