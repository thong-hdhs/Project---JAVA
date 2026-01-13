package com.example.labOdc.Controller;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.Response.TaskCommentResponse;
import com.example.labOdc.DTO.TaskCommentDTO;
import com.example.labOdc.Model.TaskComment;
import com.example.labOdc.Service.TaskCommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taskcomments")
@AllArgsConstructor
public class TaskCommentController {

    private final TaskCommentService service;

    // Tạo comment
    @PostMapping
    public ApiResponse<TaskCommentResponse> create(
            @RequestBody TaskCommentDTO taskCommentDTO) {

        TaskComment comment = service.create(taskCommentDTO);
        return ApiResponse.success(
                TaskCommentResponse.fromEntity(comment),
                "Tạo comment thành công",
                HttpStatus.CREATED
        );
    }

    // Lấy tất cả comment
    @GetMapping
    public ApiResponse<List<TaskCommentResponse>> getAll() {
        return ApiResponse.success(
                service.getAll().stream()
                        .map(TaskCommentResponse::fromEntity)
                        .toList(),
                "OK",
                HttpStatus.OK
        );
    }

    // Lấy comment theo task
    @GetMapping("/task/{taskId}")
    public ApiResponse<List<TaskCommentResponse>> getByTaskId(
            @PathVariable String taskId) {

        return ApiResponse.success(
                service.getByTaskId(taskId).stream()
                        .map(TaskCommentResponse::fromEntity)
                        .toList(),
                "OK",
                HttpStatus.OK
        );
    }

    // Lấy chi tiết
    @GetMapping("/{id}")
    public ApiResponse<TaskCommentResponse> getById(
            @PathVariable String id) {

        return ApiResponse.success(
                TaskCommentResponse.fromEntity(service.getById(id)),
                "OK",
                HttpStatus.OK
        );
    }

    // Update comment
    @PutMapping("/{id}")
    public ApiResponse<TaskCommentResponse> update(
            @PathVariable String id,
            @RequestBody TaskCommentDTO taskCommentDTO) {

        return ApiResponse.success(
                TaskCommentResponse.fromEntity(service.update(id, taskCommentDTO)),
                "Cập nhật thành công",
                HttpStatus.OK
        );
    }

    // Xóa comment
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.success(
                "Xóa thành công",
                "OK",
                HttpStatus.OK
        );
    }
}

