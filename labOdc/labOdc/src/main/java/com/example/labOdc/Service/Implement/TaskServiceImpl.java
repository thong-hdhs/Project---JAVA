package com.example.labOdc.Service.Implement;

import com.example.labOdc.DTO.Response.TaskResponse;
import com.example.labOdc.DTO.TaskDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Task;
import com.example.labOdc.Repository.TaskRepository;
import com.example.labOdc.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public Task createTask(TaskDTO dto, String createdBy) {
        Task task = Task.builder()
                .projectId(dto.getProjectId())
                .assignedTo(dto.getAssignedTo())
                .createdBy(createdBy)
                .taskName(dto.getTaskName())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .status(Task.Status.TODO)
                .startDate(dto.getStartDate())
                .dueDate(dto.getDueDate())
                .estimatedHours(dto.getEstimatedHours())
                .excelTemplateUrl(dto.getExcelTemplateUrl())
                .attachments(dto.getAttachments())
                .build();

        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(TaskDTO dto, String taskId) {
        Task task = getTaskById(taskId);

        task.setTaskName(dto.getTaskName());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setStartDate(dto.getStartDate());
        task.setDueDate(dto.getDueDate());
        task.setEstimatedHours(dto.getEstimatedHours());
        task.setExcelTemplateUrl(dto.getExcelTemplateUrl());
        task.setAttachments(dto.getAttachments());

        return taskRepository.save(task);
    }

    @Override
    public Task updateStatus(String taskId, Task.Status status) {
        Task task = getTaskById(taskId);
        task.setStatus(status);

        if (status == Task.Status.DONE) {
            task.setCompletedDate(LocalDate.now());
        }

        return taskRepository.save(task);
    }

    @Override
    public Task assignTask(String taskId, String talentId) {
        Task task = getTaskById(taskId);
        task.setAssignedTo(talentId);
        task.setStatus(Task.Status.IN_PROGRESS);

        return taskRepository.save(task);
    }

    @Override
    public Task completeTask(String taskId) {
        Task task = getTaskById(taskId);
        task.setStatus(Task.Status.DONE);
        task.setCompletedDate(LocalDate.now());

        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(String taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Task"));
    }

    @Override
    public List<Task> getTasksByProject(String projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @Override
    public List<Task> getTasksByAssignee(String talentId) {
        return taskRepository.findByAssignedTo(talentId);
    }

    @Override
    public List<Task> getTasksByCreator(String userId) {
        return taskRepository.findByCreatedBy(userId);
    }

    @Override
    public void deleteTask(String taskId) {
        Task task = getTaskById(taskId);
        taskRepository.delete(task);
    }
}