package com.example.projectcrud.controller;

import com.example.projectcrud.dto.projectteam.ProjectTeamCreateDTO;
import com.example.projectcrud.dto.projectteam.ProjectTeamUpdateDTO;
import com.example.projectcrud.response.projectteam.ProjectTeamResponse;
import com.example.projectcrud.service.ProjectTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-teams")
public class ProjectTeamController {

    @Autowired
    private ProjectTeamService projectTeamService;

    @PostMapping
    public ResponseEntity<ProjectTeamResponse> createProjectTeam(@RequestBody ProjectTeamCreateDTO projectTeamCreateDTO) {
        ProjectTeamResponse response = projectTeamService.createProjectTeam(projectTeamCreateDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectTeamResponse> getProjectTeamById(@PathVariable Long id) {
        ProjectTeamResponse response = projectTeamService.getProjectTeamById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProjectTeamResponse>> getAllProjectTeams() {
        List<ProjectTeamResponse> responses = projectTeamService.getAllProjectTeams();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectTeamResponse> updateProjectTeam(@PathVariable Long id, @RequestBody ProjectTeamUpdateDTO projectTeamUpdateDTO) {
        ProjectTeamResponse response = projectTeamService.updateProjectTeam(id, projectTeamUpdateDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectTeam(@PathVariable Long id) {
        projectTeamService.deleteProjectTeam(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}