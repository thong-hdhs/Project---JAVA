package com.example.labOdc.Service;

import java.util.List;

import com.example.labOdc.DTO.ProjectDTO;
import com.example.labOdc.Model.Project;

public interface ProjectService {
    Project createProject(ProjectDTO dto);

    List<Project> getAllProjects();

    Project getProjectById(String id);

    Project updateProject(ProjectDTO dto, String id);

    void deleteProject(String id);
}
