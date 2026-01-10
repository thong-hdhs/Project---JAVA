package com.example.labOdc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.labOdc.Model.ProjectMentor;

@Repository
public interface ProjectMentorRepository extends JpaRepository<ProjectMentor, String> {
    boolean existsByProjectIdAndMentorId(String projectId, String mentorId);
}