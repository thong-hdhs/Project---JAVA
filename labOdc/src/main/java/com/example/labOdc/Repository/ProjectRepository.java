package com.example.labOdc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.labOdc.Model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
}
