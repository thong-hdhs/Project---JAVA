package com.example.projectcrud.controller;

import com.example.projectcrud.dto.projectmentor.ProjectMentorCreateDTO;
import com.example.projectcrud.dto.projectmentor.ProjectMentorUpdateDTO;
import com.example.projectcrud.response.projectmentor.ProjectMentorResponse;
import com.example.projectcrud.service.ProjectMentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-mentors")
public class ProjectMentorController {

    @Autowired
    private ProjectMentorService projectMentorService;

    @PostMapping
    public ResponseEntity<ProjectMentorResponse> createProjectMentor(@RequestBody ProjectMentorCreateDTO projectMentorCreateDTO) {
        ProjectMentorResponse response = projectMentorService.createProjectMentor(projectMentorCreateDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectMentorResponse> getProjectMentorById(@PathVariable Long id) {
        ProjectMentorResponse response = projectMentorService.getProjectMentorById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProjectMentorResponse>> getAllProjectMentors() {
        List<ProjectMentorResponse> responses = projectMentorService.getAllProjectMentors();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectMentorResponse> updateProjectMentor(@PathVariable Long id, @RequestBody ProjectMentorUpdateDTO projectMentorUpdateDTO) {
        ProjectMentorResponse response = projectMentorService.updateProjectMentor(id, projectMentorUpdateDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectMentor(@PathVariable Long id) {
        projectMentorService.deleteProjectMentor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}