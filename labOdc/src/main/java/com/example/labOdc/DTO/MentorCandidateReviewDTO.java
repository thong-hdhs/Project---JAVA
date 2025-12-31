package com.example.labOdc.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorCandidateReviewDTO {
    private String id;
    private String mentorId;
    private String talentId;
    private String projectId;
    private BigDecimal rating;
    private String comments;
    private String status; // PENDING / APPROVED / REJECTED
    private String reviewedById;
    private LocalDateTime reviewedAt;
}
