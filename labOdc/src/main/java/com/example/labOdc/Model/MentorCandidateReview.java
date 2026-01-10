package com.example.labOdc.Model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "mentor_candidate_reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorCandidateReview {
    @Id
    private String id;

    private String mentorId;

    private String talentId;

    private String projectId;

    private BigDecimal rating;

    @Column(columnDefinition = "text")
    private String comments;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String reviewedById;

    private LocalDateTime reviewedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }

    @PrePersist
    public void prePersist() {
        if (this.id == null) this.id = java.util.UUID.randomUUID().toString();
        if (this.status == null) this.status = Status.PENDING;
        if (this.reviewedAt == null && this.status != Status.PENDING) this.reviewedAt = LocalDateTime.now();
    }
}
