package com.example.projectcrud.repository;

import com.example.projectcrud.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Additional query methods can be defined here if needed
}