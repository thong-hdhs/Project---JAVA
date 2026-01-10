package com.example.projectcrud.repository;

import com.example.projectcrud.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    // Additional query methods can be defined here if needed
}