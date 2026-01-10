package com.example.labOdc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.labOdc.Model.ProjectTeam;

@Repository
public interface ProjectTeamRepository extends JpaRepository<ProjectTeam, String> {
    boolean existsByProjectIdAndTalentId(String projectId, String talentId);
}