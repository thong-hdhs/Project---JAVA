package com.example.projectcrud.service;

import com.example.projectcrud.dto.project.ProjectCreateDTO;
import com.example.projectcrud.dto.project.ProjectUpdateDTO;
import com.example.projectcrud.model.Project;
import com.example.projectcrud.response.project.ProjectResponse;

import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(ProjectCreateDTO projectCreateDTO);
    ProjectResponse updateProject(Long projectId, ProjectUpdateDTO projectUpdateDTO);
    ProjectResponse getProjectById(Long projectId);
    List<ProjectResponse> getAllProjects();
    void deleteProject(Long projectId);
}