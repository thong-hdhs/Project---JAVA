package com.example.projectcrud.controller;

import com.example.projectcrud.dto.project.ProjectCreateDTO;
import com.example.projectcrud.dto.project.ProjectUpdateDTO;
import com.example.projectcrud.response.project.ProjectResponse;
import com.example.projectcrud.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectCreateDTO projectCreateDTO) {
        ProjectResponse projectResponse = projectService.createProject(projectCreateDTO);
        return new ResponseEntity<>(projectResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        ProjectResponse projectResponse = projectService.getProjectById(id);
        return new ResponseEntity<>(projectResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        List<ProjectResponse> projectResponses = projectService.getAllProjects();
        return new ResponseEntity<>(projectResponses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @RequestBody ProjectUpdateDTO projectUpdateDTO) {
        ProjectResponse projectResponse = projectService.updateProject(id, projectUpdateDTO);
        return new ResponseEntity<>(projectResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}