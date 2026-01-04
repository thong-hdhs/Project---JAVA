package com.example.labOdc.DTO.Response;

import com.example.labOdc.Model.CompanyRiskType;
import com.example.labOdc.Model.RiskSeverity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRiskRecordResponse {

    private String id;
    private String companyName;
    private String companyTaxCode;
    private String projectName;
    private String projectCode;
    private CompanyRiskType riskType;
    private RiskSeverity severity;
    private String description;
    private String recordedByName;
    private LocalDateTime recordedAt;
}