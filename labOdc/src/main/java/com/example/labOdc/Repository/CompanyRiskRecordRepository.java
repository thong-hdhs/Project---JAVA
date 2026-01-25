package com.example.labOdc.Repository;

import com.example.labOdc.Model.CompanyRiskRecord;
import com.example.labOdc.Model.CompanyRiskType;
import com.example.labOdc.Model.RiskSeverity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRiskRecordRepository
        extends JpaRepository<CompanyRiskRecord, String> {

    List<CompanyRiskRecord> findByCompanyId(String companyId);

    List<CompanyRiskRecord> findByProjectId(String projectId);

    List<CompanyRiskRecord> findByRiskType(CompanyRiskType riskType);

    List<CompanyRiskRecord> findBySeverity(RiskSeverity severity);

    boolean existsByCompanyIdAndSeverity(String companyId, RiskSeverity severity);

    long countByCompanyIdAndSeverity(String companyId, RiskSeverity severity);
}