package com.example.labOdc.Service.Implement;

import com.example.labOdc.DTO.Response.TaskResponse;
import com.example.labOdc.DTO.TaskDTO;
import com.example.labOdc.Model.Task;
import com.example.labOdc.Repository.TaskRepository;
import com.example.labOdc.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskResponse createTask(TaskDTO taskDTO, String creatorId) {

        Task task = Task.builder()
                .projectId(taskDTO.getProjectId())
                .assignedTo(taskDTO.getAssignedTo())
                .createdBy(creatorId)
                .taskName(taskDTO.getTaskName())
                .description(taskDTO.getDescription())
                .priority(taskDTO.getPriority())
                .status(taskDTO.getStatus())
                .startDate(taskDTO.getStartDate())
                .dueDate(taskDTO.getDueDate())
                .estimatedHours(taskDTO.getEstimatedHours())
                .actualHours(taskDTO.getActualHours())
                .excelTemplateUrl(taskDTO.getExcelTemplateUrl())
                .attachments(taskDTO.getAttachments())
                .build();

        return TaskResponse.fromEntity(taskRepository.save(task));
    }

    @Override
    public TaskResponse updateTask(String id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTaskName(taskDTO.getTaskName());
        task.setDescription(taskDTO.getDescription());
        task.setPriority(taskDTO.getPriority());
        task.setStatus(taskDTO.getStatus());
        task.setAssignedTo(taskDTO.getAssignedTo());
        task.setStartDate(taskDTO.getStartDate());
        task.setDueDate(taskDTO.getDueDate());
        task.setCompletedDate(taskDTO.getCompletedDate());
        task.setEstimatedHours(taskDTO.getEstimatedHours());
        task.setActualHours(taskDTO.getActualHours());
        task.setExcelTemplateUrl(taskDTO.getExcelTemplateUrl());
        task.setAttachments(taskDTO.getAttachments());

        return TaskResponse.fromEntity(taskRepository.save(task));
    }

    @Override
    public TaskResponse getTaskById(String id) {
        return taskRepository.findById(id)
                .map(TaskResponse::fromEntity)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Override
    public List<TaskResponse> getTasksByProject(String projectId) {
        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @Override
    public List<TaskResponse> getTasksByAssignee(String assignedTo) {
        return taskRepository.findByAssignedTo(assignedTo)
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @Override
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }
}