package com.example.labOdc.Service;

import java.util.List;

import com.example.labOdc.DTO.ProjectMentorDTO;
import com.example.labOdc.Model.ProjectMentor;

public interface ProjectMentorService {
    ProjectMentor createProjectMentor(ProjectMentorDTO dto);

    List<ProjectMentor> getAllProjectMentor();

    ProjectMentor getProjectMentorById(String id);

    ProjectMentor updateProjectMentor(ProjectMentorDTO dto, String id);

    void deleteProjectMentor(String id);
}