package com.example.labOdc.DTO;

import com.example.labOdc.Model.CompanyRiskType;
import com.example.labOdc.Model.RiskSeverity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRiskRecordDTO {

    @NotBlank(message = "companyId is required")
    private String companyId;

    private String projectId;  

    @NotNull(message = "riskType is required")
    private CompanyRiskType riskType;

    @NotNull(message = "severity is required")
    private RiskSeverity severity;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "recordedById is required")
    private String recordedById;
}