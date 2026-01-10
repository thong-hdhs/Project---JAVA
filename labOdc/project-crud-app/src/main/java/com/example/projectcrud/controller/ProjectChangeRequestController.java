package com.example.projectcrud.controller;

import com.example.projectcrud.api.ApiResponse;
import com.example.projectcrud.dto.projectchangerequest.ProjectChangeRequestCreateDTO;
import com.example.projectcrud.dto.projectchangerequest.ProjectChangeRequestUpdateDTO;
import com.example.projectcrud.model.ProjectChangeRequest;
import com.example.projectcrud.response.projectchangerequest.ProjectChangeRequestResponse;
import com.example.projectcrud.service.ProjectChangeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-change-requests")
public class ProjectChangeRequestController {

    @Autowired
    private ProjectChangeRequestService projectChangeRequestService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectChangeRequestResponse>> createProjectChangeRequest(
            @RequestBody ProjectChangeRequestCreateDTO createDTO) {
        ProjectChangeRequestResponse response = projectChangeRequestService.createProjectChangeRequest(createDTO);
        return new ResponseEntity<>(new ApiResponse<>(true, response), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectChangeRequestResponse>> getProjectChangeRequest(@PathVariable Long id) {
        ProjectChangeRequestResponse response = projectChangeRequestService.getProjectChangeRequestById(id);
        return new ResponseEntity<>(new ApiResponse<>(true, response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectChangeRequestResponse>>> getAllProjectChangeRequests() {
        List<ProjectChangeRequestResponse> responses = projectChangeRequestService.getAllProjectChangeRequests();
        return new ResponseEntity<>(new ApiResponse<>(true, responses), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectChangeRequestResponse>> updateProjectChangeRequest(
            @PathVariable Long id, @RequestBody ProjectChangeRequestUpdateDTO updateDTO) {
        ProjectChangeRequestResponse response = projectChangeRequestService.updateProjectChangeRequest(id, updateDTO);
        return new ResponseEntity<>(new ApiResponse<>(true, response), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProjectChangeRequest(@PathVariable Long id) {
        projectChangeRequestService.deleteProjectChangeRequest(id);
        return new ResponseEntity<>(new ApiResponse<>(true, null), HttpStatus.NO_CONTENT);
    }
}