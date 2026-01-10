package com.example.labOdc.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.ProjectApplicationDTO;
import com.example.labOdc.DTO.Response.ProjectApplicationResponse;
import com.example.labOdc.Model.ProjectApplication;
import com.example.labOdc.Service.ProjectApplicationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/applications")
public class ProjectApplicationController {
    private final ProjectApplicationService applicationService;

    @PostMapping("/")
    public ApiResponse<ProjectApplicationResponse> create(@Valid @RequestBody ProjectApplicationDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ApiResponse.error(errorMessages);
        }
        ProjectApplication pa = applicationService.createApplication(dto);
        return ApiResponse.success(ProjectApplicationResponse.from(pa), "Created", HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ApiResponse<List<ProjectApplicationResponse>> getAll() {
        List<ProjectApplication> list = applicationService.getAllApplications();
        return ApiResponse.success(list.stream().map(ProjectApplicationResponse::from).toList(), "OK", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ApiResponse<ProjectApplicationResponse> getById(@PathVariable String id) {
        ProjectApplication pa = applicationService.getApplicationById(id);
        return ApiResponse.success(ProjectApplicationResponse.from(pa), "OK", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.ok("Deleted");
    }

    @PutMapping("/{id}")
    public ApiResponse<ProjectApplicationResponse> update(@Valid @RequestBody ProjectApplicationDTO dto, @PathVariable String id) {
        ProjectApplication pa = applicationService.updateApplication(dto, id);
        return ApiResponse.success(ProjectApplicationResponse.from(pa), "Updated", HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    public ApiResponse<List<ProjectApplicationResponse>> getByProject(@PathVariable String projectId) {
        return ApiResponse.success(applicationService.findByProjectId(projectId).stream().map(ProjectApplicationResponse::from).toList(), "OK", HttpStatus.OK);
    }

    @GetMapping("/talent/{talentId}")
    public ApiResponse<List<ProjectApplicationResponse>> getByTalent(@PathVariable String talentId) {
        return ApiResponse.success(applicationService.findByTalentId(talentId).stream().map(ProjectApplicationResponse::from).toList(), "OK", HttpStatus.OK);
    }
}
