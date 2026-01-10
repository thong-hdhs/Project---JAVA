package com.example.projectcrud.service;

import com.example.projectcrud.dto.projectmentor.ProjectMentorCreateDTO;
import com.example.projectcrud.dto.projectmentor.ProjectMentorUpdateDTO;
import com.example.projectcrud.model.ProjectMentor;
import com.example.projectcrud.response.projectmentor.ProjectMentorResponse;

import java.util.List;

public interface ProjectMentorService {
    ProjectMentorResponse createProjectMentor(ProjectMentorCreateDTO projectMentorCreateDTO);
    ProjectMentorResponse updateProjectMentor(Long id, ProjectMentorUpdateDTO projectMentorUpdateDTO);
    void deleteProjectMentor(Long id);
    ProjectMentorResponse getProjectMentorById(Long id);
    List<ProjectMentorResponse> getAllProjectMentors();
}