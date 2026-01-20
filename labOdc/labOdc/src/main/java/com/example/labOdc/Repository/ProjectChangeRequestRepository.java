package com.example.labOdc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.labOdc.Model.ProjectChangeRequest;

@Repository
public interface ProjectChangeRequestRepository extends JpaRepository<ProjectChangeRequest, String> {
}