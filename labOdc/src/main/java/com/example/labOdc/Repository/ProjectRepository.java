package com.example.labOdc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.labOdc.Model.Project;

public interface ProjectRepository extends JpaRepository<Project, String> {
    boolean existsByProjectCode(String projectCode);
}
