package com.example.labOdc.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.labOdc.Model.MentorInvitationStatus;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MentorInvitationDTO {
    private String projectId;
    private String mentorId;
    private String invitedBy;
    private String invitationMessage;
    private BigDecimal proposedFeePercentage;

    private MentorInvitationStatus status;
    private LocalDateTime respondedAt;
}