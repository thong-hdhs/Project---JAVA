package com.example.projectcrud.repository;

import com.example.projectcrud.model.MentorInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorInvitationRepository extends JpaRepository<MentorInvitation, Long> {
    // Additional query methods can be defined here if needed
}