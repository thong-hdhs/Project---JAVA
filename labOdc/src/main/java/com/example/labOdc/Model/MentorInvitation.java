package com.example.labOdc.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mentor_invitations", uniqueConstraints = @UniqueConstraint(name = "uk_project_mentor", columnNames = {
        "project_id", "mentor_id" }))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MentorInvitation {

    @Id
    @Column(name = "id", length = 36, nullable = false, updatable = false)
    private String id;

    @Column(name = "project_id", length = 36, nullable = false)
    private String projectId;

    @Column(name = "mentor_id", length = 36, nullable = false)
    private String mentorId;

    @Column(name = "invited_by", length = 36)
    private String invitedBy;

    @Lob
    @Column(name = "invitation_message")
    private String invitationMessage;

    @Column(name = "proposed_fee_percentage")
    private BigDecimal proposedFeePercentage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private MentorInvitationStatus status = MentorInvitationStatus.PENDING;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void ensureId() {
        if (this.id == null)
            this.id = UUID.randomUUID().toString();
    }
}