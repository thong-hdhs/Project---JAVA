package com.example.labOdc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.labOdc.Model.MentorInvitation;

@Repository
public interface MentorInvitationRepository extends JpaRepository<MentorInvitation, String> {
    boolean existsByProjectIdAndMentorId(String projectId, String mentorId);
}