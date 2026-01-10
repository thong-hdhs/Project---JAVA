package com.example.projectcrud.repository;

import com.example.projectcrud.model.ProjectChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectChangeRequestRepository extends JpaRepository<ProjectChangeRequest, Long> {
}