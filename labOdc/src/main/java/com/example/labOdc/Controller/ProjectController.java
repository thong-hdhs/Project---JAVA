package com.example.labOdc.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.ProjectDTO;
import com.example.labOdc.DTO.Response.ProjectResponse;
import com.example.labOdc.Model.Project;
import com.example.labOdc.Service.ProjectService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/")
    public ApiResponse<ProjectResponse> createProject(@Valid @RequestBody ProjectDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ApiResponse.error(errorMessages);
        }

        Project project = projectService.createProject(dto);
        return ApiResponse.success(ProjectResponse.fromProject(project), "Thanh cong", HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ApiResponse<List<ProjectResponse>> getAllProject() {
        List<Project> list = projectService.getAllProjects();
        return ApiResponse.success(list.stream().map(ProjectResponse::fromProject).toList(), "Thanh cong",
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ApiResponse<ProjectResponse> getProjectById(@PathVariable String id) {
        Project project = projectService.getProjectById(id);
        return ApiResponse.success(ProjectResponse.fromProject(project), "Thanh cong", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ApiResponse<ProjectResponse> updateProject(@Valid @RequestBody ProjectDTO dto, @PathVariable String id) {
        Project project = projectService.updateProject(dto, id);
        return ApiResponse.success(ProjectResponse.fromProject(project), "Thanh cong", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ApiResponse.success("Xoa thanh cong", "Thanh cong", HttpStatus.OK);
    }
}