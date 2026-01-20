package com.example.labOdc.Service;

import com.example.labOdc.DTO.Response.TaskResponse;
import com.example.labOdc.DTO.TaskDTO;
import com.example.labOdc.Model.Task;

import java.util.List;

public interface TaskService {

    Task createTask(TaskDTO dto, String createdBy);

    Task updateTask(TaskDTO dto, String taskId);

    Task updateStatus(String taskId, Task.Status status);

    Task assignTask(String taskId, String talentId);

    Task completeTask(String taskId);

    Task getTaskById(String taskId);

    List<Task> getTasksByProject(String projectId);

    List<Task> getTasksByAssignee(String talentId);

    List<Task> getTasksByCreator(String userId);

    void deleteTask(String taskId);
}