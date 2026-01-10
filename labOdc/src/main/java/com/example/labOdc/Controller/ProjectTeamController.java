package com.example.labOdc.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.ProjectTeamDTO;
import com.example.labOdc.DTO.Response.ProjectTeamResponse;
import com.example.labOdc.Model.ProjectTeam;
import com.example.labOdc.Service.ProjectTeamService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/project-teams")
public class ProjectTeamController {

    private final ProjectTeamService projectTeamService;

    @PostMapping("/")
    public ApiResponse<ProjectTeamResponse> create(@Valid @RequestBody ProjectTeamDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ApiResponse.error(errorMessages);
        }

        ProjectTeam pt = projectTeamService.createProjectTeam(dto);
        return ApiResponse.success(ProjectTeamResponse.fromProjectTeam(pt), "Thanh cong", HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ApiResponse<List<ProjectTeamResponse>> getAll() {
        List<ProjectTeam> list = projectTeamService.getAllProjectTeam();
        return ApiResponse.success(list.stream().map(ProjectTeamResponse::fromProjectTeam).toList(),
                "Thanh cong", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ApiResponse<ProjectTeamResponse> getById(@PathVariable String id) {
        ProjectTeam pt = projectTeamService.getProjectTeamById(id);
        return ApiResponse.success(ProjectTeamResponse.fromProjectTeam(pt), "Thanh cong", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ApiResponse<ProjectTeamResponse> update(@Valid @RequestBody ProjectTeamDTO dto, @PathVariable String id) {
        ProjectTeam pt = projectTeamService.updateProjectTeam(dto, id);
        return ApiResponse.success(ProjectTeamResponse.fromProjectTeam(pt), "Thanh cong", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        projectTeamService.deleteProjectTeam(id);
        return ApiResponse.success("Xoa thanh cong", "Thanh cong", HttpStatus.OK);
    }
}