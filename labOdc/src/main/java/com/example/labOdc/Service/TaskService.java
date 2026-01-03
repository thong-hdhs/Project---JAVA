package com.example.labOdc.Service;

import com.example.labOdc.DTO.Response.TaskResponse;
import com.example.labOdc.DTO.TaskDTO;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskDTO taskDTO, String creatorId);

    TaskResponse updateTask(String id, TaskDTO taskDTO);

    TaskResponse getTaskById(String id);

    List<TaskResponse> getTasksByProject(String projectId);

    List<TaskResponse> getTasksByAssignee(String assignedTo);

    void deleteTask(String id);
}