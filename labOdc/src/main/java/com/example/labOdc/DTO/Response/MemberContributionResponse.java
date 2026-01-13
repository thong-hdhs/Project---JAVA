package com.example.labOdc.DTO.Response;

import com.example.labOdc.Model.MemberContribution;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class MemberContributionResponse {

    private String id;
    private String projectId;
    private String talentId;
    private MemberContribution.ContributionType contributionType;
    private String description;
    private BigDecimal score;
    private String recordedBy;
    private LocalDateTime recordedAt;

    public static MemberContributionResponse fromEntity(MemberContribution memberContribution) {
        return MemberContributionResponse.builder()
                .id(memberContribution.getId())
                .projectId(memberContribution.getProjectId())
                .talentId(memberContribution.getTalentId())
                .contributionType(memberContribution.getContributionType())
                .description(memberContribution.getDescription())
                .score(memberContribution.getScore())
                .recordedBy(memberContribution.getRecordedBy())
                .recordedAt(memberContribution.getRecordedAt())
                .build();
    }
}

