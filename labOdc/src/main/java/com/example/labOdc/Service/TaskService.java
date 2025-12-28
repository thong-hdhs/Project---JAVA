package com.example.labOdc.Service;

import com.example.labOdc.DTO.Response.TaskResponse;
import com.example.labOdc.DTO.TaskDTO;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskDTO DTO, String createdBy);

    TaskResponse updateTask(String taskId, TaskDTO DTO);

    void deleteTask(String taskId);

    TaskResponse getTaskById(String taskId);

    List<TaskResponse> getTasksByProject(String projectId);

    List<TaskResponse> getTasksByAssignee(String userId);
}
