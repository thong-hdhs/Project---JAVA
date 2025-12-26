package com.example.projectcrud.repository;

import com.example.projectcrud.model.ProjectMentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMentorRepository extends JpaRepository<ProjectMentor, Long> {
    // Additional query methods can be defined here if needed
}