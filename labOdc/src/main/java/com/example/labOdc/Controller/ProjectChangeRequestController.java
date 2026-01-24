package com.example.labOdc.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.ProjectChangeRequestDTO;
import com.example.labOdc.DTO.Action.ReviewNotesDTO;
import com.example.labOdc.DTO.Response.ProjectChangeRequestResponse;
import com.example.labOdc.Model.ProjectChangeRequest;
import com.example.labOdc.Service.ProjectChangeRequestService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/project-change-requests")
public class ProjectChangeRequestController {

    private final ProjectChangeRequestService projectChangeRequestService;

    @PostMapping("/")
    public ApiResponse<ProjectChangeRequestResponse> create(@Valid @RequestBody ProjectChangeRequestDTO dto,
            BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ApiResponse.error(errorMessages);
        }

        ProjectChangeRequest pcr = projectChangeRequestService.createProjectChangeRequest(dto);
        return ApiResponse.success(ProjectChangeRequestResponse.fromProjectChangeRequest(pcr), "Thanh cong",
                HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ApiResponse<List<ProjectChangeRequestResponse>> getAll() {
        List<ProjectChangeRequest> list = projectChangeRequestService.getAllProjectChangeRequest();
        return ApiResponse.success(
                list.stream().map(ProjectChangeRequestResponse::fromProjectChangeRequest).toList(),
                "Thanh cong",
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ApiResponse<ProjectChangeRequestResponse> getById(@PathVariable String id) {
        ProjectChangeRequest pcr = projectChangeRequestService.getProjectChangeRequestById(id);
        return ApiResponse.success(ProjectChangeRequestResponse.fromProjectChangeRequest(pcr), "Thanh cong",
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ApiResponse<ProjectChangeRequestResponse> update(@Valid @RequestBody ProjectChangeRequestDTO dto,
            @PathVariable String id) {
        ProjectChangeRequest pcr = projectChangeRequestService.updateProjectChangeRequest(dto, id);
        return ApiResponse.success(ProjectChangeRequestResponse.fromProjectChangeRequest(pcr), "Thanh cong",
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        projectChangeRequestService.deleteProjectChangeRequest(id);
        return ApiResponse.success("Xoa thanh cong", "Thanh cong", HttpStatus.OK);
    }

    // --------- workflow chuẩn ---------

    @GetMapping("/by-project/{projectId}")
    public ApiResponse<List<ProjectChangeRequestResponse>> getByProject(@PathVariable String projectId) {
        List<ProjectChangeRequest> list = projectChangeRequestService.getByProjectId(projectId);
        return ApiResponse.success(
                list.stream().map(ProjectChangeRequestResponse::fromProjectChangeRequest).toList(),
                "Thanh cong",
                HttpStatus.OK);
    }

    @PutMapping("/{id}/approve")
    public ApiResponse<ProjectChangeRequestResponse> approve(
            @PathVariable String id,
            @RequestBody(required = false) ReviewNotesDTO body) {

        String notes = body != null ? body.getReviewNotes() : null;
        ProjectChangeRequest pcr = projectChangeRequestService.approve(id, notes);

        return ApiResponse.success(ProjectChangeRequestResponse.fromProjectChangeRequest(pcr), "Approved",
                HttpStatus.OK);
    }

    @PutMapping("/{id}/reject")
    public ApiResponse<ProjectChangeRequestResponse> reject(
            @PathVariable String id,
            @RequestBody ReviewNotesDTO body) {

        ProjectChangeRequest pcr = projectChangeRequestService.reject(id, body.getReviewNotes());
        return ApiResponse.success(ProjectChangeRequestResponse.fromProjectChangeRequest(pcr), "Rejected",
                HttpStatus.OK);
    }

    @PutMapping("/{id}/cancel")
    public ApiResponse<ProjectChangeRequestResponse> cancel(@PathVariable String id) {
        ProjectChangeRequest pcr = projectChangeRequestService.cancel(id);
        return ApiResponse.success(ProjectChangeRequestResponse.fromProjectChangeRequest(pcr), "Cancelled",
                HttpStatus.OK);
    }
}