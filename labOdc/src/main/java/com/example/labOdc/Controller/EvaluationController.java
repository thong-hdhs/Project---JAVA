package com.example.labOdc.Controller;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.EvaluationDTO;
import com.example.labOdc.DTO.Response.EvaluationResponse;
import com.example.labOdc.Model.Evaluation;
import com.example.labOdc.Service.EvaluationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
@AllArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;
//    Tạo evaluation evaluatorType xác định: COMPANY / MENTOR / TALENT / LAB_ADMIN
    @PostMapping("/{evaluatorId}")
    @PreAuthorize("hasAnyRole('COMPANY','MENTOR','TALENT','LAB_ADMIN')")
    public ApiResponse<EvaluationResponse> createEvaluation(
            @PathVariable String evaluatorId,
            @RequestParam Evaluation.EvaluatorType evaluatorType,
            @Valid @RequestBody EvaluationDTO dto) {

        Evaluation evaluation = evaluationService.createEvaluation(
                dto,
                evaluatorId,
                evaluatorType);

        return ApiResponse.success(
                EvaluationResponse.fromEntity(evaluation),
                "Tạo đánh giá thành công",
                HttpStatus.CREATED);
    }
//  * Lấy tất cả evaluation
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('COMPANY','MENTOR','TALENT','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<List<EvaluationResponse>> getAll() {
        return ApiResponse.success(
                evaluationService.getAll()
                        .stream()
                        .map(EvaluationResponse::fromEntity)
                        .toList(),
                "Thành công",
                HttpStatus.OK);
    }
    //    Lấy evaluation theo ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('COMPANY','MENTOR','TALENT','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<EvaluationResponse> getById(@PathVariable String id) {
        return ApiResponse.success(
                EvaluationResponse.fromEntity(evaluationService.getById(id)),
                "Thành công",
                HttpStatus.OK);
    }
    //    Lấy evaluation theo project
    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAnyRole('COMPANY','MENTOR','TALENT','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<List<EvaluationResponse>> getByProject(@PathVariable String projectId) {
        return ApiResponse.success(
                evaluationService.getByProject(projectId)
                        .stream()
                        .map(EvaluationResponse::fromEntity)
                        .toList(),
                "Thành công",
                HttpStatus.OK);
    }
//    Lấy evaluation theo người được đánh giá
    @GetMapping("/evaluated/{evaluatedId}")
    @PreAuthorize("hasAnyRole('COMPANY','MENTOR','TALENT','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<List<EvaluationResponse>> getByEvaluated(@PathVariable String evaluatedId) {
        return ApiResponse.success(
                evaluationService.getByEvaluated(evaluatedId)
                        .stream()
                        .map(EvaluationResponse::fromEntity)
                        .toList(),
                "Thành công",
                HttpStatus.OK);
    }
//    Lấy evaluation theo người đánh giá
    @GetMapping("/evaluator/{evaluatorId}")
    @PreAuthorize("hasAnyRole('COMPANY','MENTOR','TALENT','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<List<EvaluationResponse>> getByEvaluator(@PathVariable String evaluatorId) {
        return ApiResponse.success(
                evaluationService.getByEvaluator(evaluatorId)
                        .stream()
                        .map(EvaluationResponse::fromEntity)
                        .toList(),
                "Thành công",
                HttpStatus.OK);
    }
    @GetMapping("/type/{type}")
    @PreAuthorize("hasAnyRole('COMPANY','MENTOR','TALENT','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<List<EvaluationResponse>> byType(@PathVariable Evaluation.EvaluatedType type) {
        List<Evaluation> list = evaluationService.getByEvaluatedType(type);
        return ApiResponse.success(list
                        .stream()
                        .map(EvaluationResponse::fromEntity)
                        .toList(),
                "Thành công",
                HttpStatus.OK);
    }
//    Cập nhật evaluation
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('COMPANY','MENTOR','TALENT','LAB_ADMIN')")
    public ApiResponse<EvaluationResponse> updateEvaluation(
            @PathVariable String id,
            @RequestBody EvaluationDTO dto) {

        Evaluation evaluation = evaluationService.updateEvaluation(id, dto);

        return ApiResponse.success(
                EvaluationResponse.fromEntity(evaluation),
                "Cập nhật thành công",
                HttpStatus.OK);
    }
//    Xóa evaluation
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('COMPANY','MENTOR','TALENT','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<String> deleteEvaluation(@PathVariable String id) {
        evaluationService.deleteEvaluation(id);
        return ApiResponse.success("Xóa thành công", "OK", HttpStatus.OK);
    }
}
