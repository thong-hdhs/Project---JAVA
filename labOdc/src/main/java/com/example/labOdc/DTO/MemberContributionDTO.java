package com.example.labOdc.DTO;

import com.example.labOdc.Model.MemberContribution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberContributionDTO {

    private String projectId;
    private String talentId;
    private MemberContribution.ContributionType contributionType;
    private String description;
    private BigDecimal score;
    private String recordedBy;
}
