package com.example.labOdc.Controller;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.EvaluationDTO;
import com.example.labOdc.DTO.Response.EvaluationResponse;
import com.example.labOdc.Service.EvaluationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
@AllArgsConstructor
public class EvaluationController {

    private final EvaluationService service;

    @PostMapping
    public ApiResponse<EvaluationResponse> create(@RequestBody EvaluationDTO dto) {
        return ApiResponse.success(
                EvaluationResponse.fromEntity(service.create(dto)),
                "Tạo đánh giá thành công",
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ApiResponse<List<EvaluationResponse>> getAll() {
        return ApiResponse.success(
                service.getAll().stream()
                        .map(EvaluationResponse::fromEntity)
                        .toList(),
                "OK",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<EvaluationResponse> getById(@PathVariable String id) {
        return ApiResponse.success(
                EvaluationResponse.fromEntity(service.getById(id)),
                "OK",
                HttpStatus.OK
        );
    }

    @GetMapping("/project/{projectId}")
    public ApiResponse<List<EvaluationResponse>> getByProject(@PathVariable String projectId) {
        return ApiResponse.success(
                service.getByProject(projectId).stream()
                        .map(EvaluationResponse::fromEntity)
                        .toList(),
                "OK",
                HttpStatus.OK
        );
    }

    @GetMapping("/evaluator/{evaluatorId}")
    public ApiResponse<List<EvaluationResponse>> getByEvaluator(@PathVariable String evaluatorId) {
        return ApiResponse.success(
                service.getByEvaluator(evaluatorId).stream()
                        .map(EvaluationResponse::fromEntity)
                        .toList(),
                "OK",
                HttpStatus.OK
        );
    }

    @GetMapping("/evaluated/{evaluatedId}")
    public ApiResponse<List<EvaluationResponse>> getByEvaluated(@PathVariable String evaluatedId) {
        return ApiResponse.success(
                service.getByEvaluated(evaluatedId).stream()
                        .map(EvaluationResponse::fromEntity)
                        .toList(),
                "OK",
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<EvaluationResponse> update(
            @PathVariable String id,
            @RequestBody EvaluationDTO dto) {

        return ApiResponse.success(
                EvaluationResponse.fromEntity(service.update(id, dto)),
                "Cập nhật đánh giá thành công",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.success("Xóa thành công", "OK", HttpStatus.OK);
    }
}
