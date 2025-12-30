package com.example.labOdc.DTO.Response;

import java.time.LocalDateTime;

import com.example.labOdc.Model.Company;
import com.example.labOdc.Model.CompanySize;
import com.example.labOdc.Model.CompanyStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyResponse {
    private String id;
    private String userId;
    private String companyName;
    private String taxCode;
    private String address;
    private String industry;
    private String description;
    private String website;
    private CompanySize companySize;
    private CompanyStatus status;
    private String approvedById;
    private LocalDateTime approvedAt;
    private String rejectionReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CompanyResponse fromCompany(Company c) {
        return CompanyResponse.builder()
                .id(c.getId())
                .userId(c.getUser() != null ? c.getUser().getId() : null)
                .companyName(c.getCompanyName())
                .taxCode(c.getTaxCode())
                .address(c.getAddress())
                .industry(c.getIndustry())
                .description(c.getDescription())
                .website(c.getWebsite())
                .companySize(c.getCompanySize())
                .status(c.getStatus())
                .approvedById(c.getApprovedBy() != null ? c.getApprovedBy().getId() : null)
                .approvedAt(c.getApprovedAt())
                .rejectionReason(c.getRejectionReason())
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .build();
    }
}
