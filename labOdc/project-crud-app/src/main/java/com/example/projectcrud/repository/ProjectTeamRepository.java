package com.example.projectcrud.repository;

import com.example.projectcrud.model.ProjectTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTeamRepository extends JpaRepository<ProjectTeam, Long> {
    // Additional query methods can be defined here if needed
}