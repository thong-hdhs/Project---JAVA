package com.example.projectcrud.service;

import com.example.projectcrud.dto.projectteam.ProjectTeamCreateDTO;
import com.example.projectcrud.dto.projectteam.ProjectTeamUpdateDTO;
import com.example.projectcrud.model.ProjectTeam;
import com.example.projectcrud.response.projectteam.ProjectTeamResponse;

import java.util.List;

public interface ProjectTeamService {
    ProjectTeamResponse createProjectTeam(ProjectTeamCreateDTO projectTeamCreateDTO);
    ProjectTeamResponse updateProjectTeam(Long id, ProjectTeamUpdateDTO projectTeamUpdateDTO);
    void deleteProjectTeam(Long id);
    ProjectTeamResponse getProjectTeamById(Long id);
    List<ProjectTeamResponse> getAllProjectTeams();
}