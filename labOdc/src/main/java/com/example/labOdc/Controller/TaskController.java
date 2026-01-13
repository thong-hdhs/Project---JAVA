package com.example.labOdc.Controller;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.Response.TaskResponse;
import com.example.labOdc.DTO.TaskDTO;
import com.example.labOdc.Service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/")
    public ApiResponse<TaskResponse> createTask(
            @Valid @RequestBody TaskDTO taskDTO,
            BindingResult result,
            @RequestParam String creatorId
    ) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ApiResponse.error(errors);
        }

        TaskResponse response = taskService.createTask(taskDTO, creatorId);
        return ApiResponse.success(response, "Tạo task thành công", HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ApiResponse<TaskResponse> updateTask(
            @Valid @RequestBody TaskDTO taskDTO,
            BindingResult result,
            @PathVariable String id
    ) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ApiResponse.error(errors);
        }

        TaskResponse response = taskService.updateTask(id, taskDTO);
        return ApiResponse.success(response, "Cập nhật task thành công", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ApiResponse.success(
                null,
                "Xóa thành công",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<TaskResponse> getTaskById(@PathVariable String id) {
        TaskResponse response = taskService.getTaskById(id);
        return ApiResponse.success(response, "Lấy task thành công", HttpStatus.OK);
    }
    @GetMapping("/project/{projectId}")
    public ApiResponse<List<TaskResponse>> getTasksByProject(@PathVariable String projectId) {
        List<TaskResponse> list = taskService.getTasksByProject(projectId);
        return ApiResponse.success(list, "Lấy danh sách task theo project", HttpStatus.OK);
    }
    @GetMapping("/assignee/{assignedTo}")
    public ApiResponse<List<TaskResponse>> getTasksByAssignee(@PathVariable String assignedTo) {
        List<TaskResponse> list = taskService.getTasksByAssignee(assignedTo);
        return ApiResponse.success(list, "Lấy danh sách task theo người được giao", HttpStatus.OK);
    }
}

