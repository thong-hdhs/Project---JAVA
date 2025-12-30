package com.example.labOdc.DTO;

import java.time.LocalDateTime;

import com.example.labOdc.Model.CompanySize;
import com.example.labOdc.Model.CompanyStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDTO {
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
}
