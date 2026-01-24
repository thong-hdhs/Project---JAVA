package com.example.labOdc.Service;

import com.example.labOdc.DTO.CompanyRiskRecordDTO;
import com.example.labOdc.DTO.Response.CompanyRiskRecordResponse;

import java.util.List;

public interface CompanyRiskRecordService {

    CompanyRiskRecordResponse createRiskRecord(CompanyRiskRecordDTO dto);

    CompanyRiskRecordResponse getById(String id);

    List<CompanyRiskRecordResponse> getByCompanyId(String companyId);

    List<CompanyRiskRecordResponse> getByProjectId(String projectId);

    List<CompanyRiskRecordResponse> getHighRiskCompanies();
}