package com.example.labOdc.Controller;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.Response.TaskResponse;
import com.example.labOdc.DTO.TaskDTO;
import com.example.labOdc.Model.Task;
import com.example.labOdc.Service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN')
    or hasAuthority('MENTOR_ASSIGN_TASK')
""")
    public ApiResponse<TaskResponse> createTask(
            @Valid @RequestBody TaskDTO dto,
            Principal principal
    ) {
        Task task = taskService.createTask(dto, principal.getName());
        return ApiResponse.success(TaskResponse.fromEntity(task), "Tạo task thành công", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN')
    or hasAuthority('MENTOR_ASSIGN_TASK')
""")
    public ApiResponse<TaskResponse> updateTask(
            @Valid @RequestBody TaskDTO dto,
            @PathVariable String id
    ) {
        Task task = taskService.updateTask(dto, id);
        return ApiResponse.success(TaskResponse.fromEntity(task), "Cập nhật task thành công", HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN')
    or hasAuthority('MENTOR_REVIEW_TASK')
""")
    public ApiResponse<TaskResponse> updateStatus(
            @PathVariable String id,
            @RequestParam Task.Status status
    ) {
        Task task = taskService.updateStatus(id, status);
        return ApiResponse.success(TaskResponse.fromEntity(task), "Cập nhật trạng thái", HttpStatus.OK);
    }

    @PatchMapping("/{id}/assign")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN')
    or hasAuthority('MENTOR_ASSIGN_TASK')
""")
    public ApiResponse<TaskResponse> assignTask(
            @PathVariable String id,
            @RequestParam String talentId
    ) {
        Task task = taskService.assignTask(id, talentId);
        return ApiResponse.success(TaskResponse.fromEntity(task), "Giao task thành công", HttpStatus.OK);
    }

    @PatchMapping("/{id}/complete")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN')
    or hasAuthority('MENTOR_REVIEW_TASK')
    or hasAuthority('TALENT_UPDATE_TASK')
""")
    public ApiResponse<TaskResponse> completeTask(@PathVariable String id) {
        Task task = taskService.completeTask(id);
        return ApiResponse.success(TaskResponse.fromEntity(task), "Hoàn thành task", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN')
    or hasAuthority('MENTOR_REVIEW_TASK')
    or hasAuthority('TALENT_VIEW_ASSIGNED_TASK')
""")
    public ApiResponse<TaskResponse> getTaskById(@PathVariable String id) {
        Task task = taskService.getTaskById(id);
        return ApiResponse.success(TaskResponse.fromEntity(task), "OK", HttpStatus.OK);
    }
    @GetMapping("/project/{projectId}")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN')
    or hasAuthority('MENTOR_REVIEW_TASK')
    or hasAuthority('TALENT_VIEW_ASSIGNED_TASK')
""")
    public ApiResponse<List<TaskResponse>> getTasksByProject(@PathVariable String projectId) {
        List<TaskResponse> responses = taskService.getTasksByProject(projectId)
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();

        return ApiResponse.success(responses, "OK", HttpStatus.OK);
    }

    @GetMapping("/assignee/{talentId}")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN')
    or hasAuthority('MENTOR_REVIEW_TASK')
    or hasAuthority('TALENT_VIEW_ASSIGNED_TASK')
""")
    public ApiResponse<List<TaskResponse>> getTasksByAssignee(@PathVariable String talentId) {
        List<TaskResponse> responses = taskService.getTasksByAssignee(talentId)
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();

        return ApiResponse.success(responses, "OK", HttpStatus.OK);
    }

    @GetMapping("/creator/{userId}")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN')
    or hasAuthority('MENTOR_ASSIGN_TASK')
""")
    public ApiResponse<List<TaskResponse>> byCreator(@PathVariable String userId) {
        List<Task> list = taskService.getTasksByCreator(userId);
        return ApiResponse.success(list.stream().map(TaskResponse::fromEntity).toList(), "OK", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("""
    hasAnyRole('SYSTEM_ADMIN','LAB_ADMIN')
""")
    public ApiResponse<String> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ApiResponse.success("Xóa task thành công", "OK", HttpStatus.OK);
    }
}