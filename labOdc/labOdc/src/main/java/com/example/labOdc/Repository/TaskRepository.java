package com.example.labOdc.Repository;

import ch.qos.logback.core.status.Status;
import com.example.labOdc.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    List<Task> findByProjectId(String projectId);

    List<Task> findByAssignedTo(String assignedTo);

    List<Task> findByCreatedBy(String userId);

    List<Task> findByProjectIdAndStatus(String projectId, Status status);
}