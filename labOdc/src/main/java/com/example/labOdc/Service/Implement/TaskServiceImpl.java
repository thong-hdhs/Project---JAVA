package com.example.labOdc.Service.Implement;

import com.example.labOdc.DTO.Response.TaskResponse;
import com.example.labOdc.DTO.TaskDTO;
import com.example.labOdc.Model.Task;
import com.example.labOdc.Repository.TaskRepository;
import com.example.labOdc.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskResponse createTask(TaskDTO DTO, String createdBy) {

        Task task = Task.builder()
                .projectId(DTO.getProjectId())
                .assignedTo(DTO.getAssignedTo())
                .createdBy(createdBy)
                .taskName(DTO.getTaskName())
                .description(DTO.getDescription())
                .priority(DTO.getPriority())
                .status(DTO.getStatus())
                .startDate(DTO.getStartDate())
                .dueDate(DTO.getDueDate())
                .estimatedHours(DTO.getEstimatedHours())
                .excelTemplateUrl(DTO.getExcelTemplateUrl())
                .attachments(DTO.getAttachments())
                .build();

        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public TaskResponse updateTask(String taskId, TaskDTO DTO) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhiệm vụ"));

        task.setTaskName(DTO.getTaskName());
        task.setDescription(DTO.getDescription());
        task.setPriority(DTO.getPriority());
        task.setStatus(DTO.getStatus());
        task.setStartDate(DTO.getStartDate());
        task.setDueDate(DTO.getDueDate());
        task.setEstimatedHours(DTO.getEstimatedHours());
        task.setExcelTemplateUrl(DTO.getExcelTemplateUrl());
        task.setAttachments(DTO.getAttachments());

        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public void deleteTask(String taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public TaskResponse getTaskById(String taskId) {
        return taskRepository.findById(taskId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhiệm vụ"));
    }

    @Override
    public List<TaskResponse> getTasksByProject(String projectId) {
        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> getTasksByAssignee(String userId) {
        return taskRepository.findByAssignedTo(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .projectId(task.getProjectId())
                .assignedTo(task.getAssignedTo())
                .createdBy(task.getCreatedBy())
                .taskName(task.getTaskName())
                .description(task.getDescription())
                .priority(task.getPriority())
                .status(task.getStatus())
                .startDate(task.getStartDate())
                .dueDate(task.getDueDate())
                .completedDate(task.getCompletedDate())
                .estimatedHours(task.getEstimatedHours())
                .actualHours(task.getActualHours())
                .excelTemplateUrl(task.getExcelTemplateUrl())
                .attachments(task.getAttachments())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}