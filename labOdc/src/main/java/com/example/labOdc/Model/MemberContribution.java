package com.example.labOdc.Model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "member_contributions")
public class MemberContribution {
    @Id
    @Column(name = "id", length = 36, nullable = false, updatable = false)
    private String id;

    @Column(name = "project_id", nullable = false)
    private String projectId;

    @Column(name = "talent_id", nullable = false)
    private String talentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "contribution_type")
    private ContributionType contributionType;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "score")
    private BigDecimal score;

    @Column(name = "recorded_by")
    private String recordedBy;
    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;

    @PrePersist
    public void ensureId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        if (recordedAt == null) {
            recordedAt = LocalDateTime.now();
        }
    }

    public enum ContributionType {
        CODE, DESIGN, DOCUMENT, LEADERSHIP, SUPPORT, OTHER
    }
}
