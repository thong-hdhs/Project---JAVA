package com.example.labOdc.Service.Implement;

import com.example.labOdc.DTO.CompanyRiskRecordDTO;
import com.example.labOdc.DTO.Response.CompanyRiskRecordResponse;
import com.example.labOdc.Model.*;
import com.example.labOdc.Repository.*;
import com.example.labOdc.Service.CompanyRiskRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyRiskRecordServiceImpl implements CompanyRiskRecordService {

    private final CompanyRiskRecordRepository companyRiskRecordRepository;
    private final CompanyRepository companyRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public CompanyRiskRecordResponse createRiskRecord(CompanyRiskRecordDTO dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Project project = null;
        if (dto.getProjectId() != null && !dto.getProjectId().isBlank()) {
            project = projectRepository.findById(dto.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
        }

        User recordedBy = userRepository.findById(dto.getRecordedById())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CompanyRiskRecord record = CompanyRiskRecord.builder()
                .company(company)
                .project(project)
                .riskType(dto.getRiskType())
                .severity(dto.getSeverity())
                .description(dto.getDescription())
                .recordedBy(recordedBy)
                .build();

        CompanyRiskRecord saved = companyRiskRecordRepository.save(record);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyRiskRecordResponse getById(String id) {
        CompanyRiskRecord record = companyRiskRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Risk record not found"));
        return mapToResponse(record);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyRiskRecordResponse> getByCompanyId(String companyId) {
        return companyRiskRecordRepository.findByCompanyIdOrderByRecordedAtDesc(companyId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyRiskRecordResponse> getByProjectId(String projectId) {
        return companyRiskRecordRepository.findByProjectId(projectId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyRiskRecordResponse> getHighRiskCompanies() {
        return companyRiskRecordRepository.findBySeverityIn(
                List.of(RiskSeverity.HIGH, RiskSeverity.CRITICAL))
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CompanyRiskRecordResponse mapToResponse(CompanyRiskRecord record) {
        return CompanyRiskRecordResponse.builder()
                .id(record.getId())
                .companyName(record.getCompany().getCompanyName())
                .companyTaxCode(record.getCompany().getTaxCode())
                .projectName(record.getProject() != null ? record.getProject().getProjectName() : null)
                .projectCode(record.getProject() != null ? record.getProject().getProjectCode() : null)
                .riskType(record.getRiskType())
                .severity(record.getSeverity())
                .description(record.getDescription())
                .recordedByName(record.getRecordedBy().getFullName())
                .recordedAt(record.getRecordedAt())
                .build();
    }
}