package com.example.projectcrud.serviceimpl;

import com.example.projectcrud.dto.projectmentor.ProjectMentorCreateDTO;
import com.example.projectcrud.dto.projectmentor.ProjectMentorUpdateDTO;
import com.example.projectcrud.model.ProjectMentor;
import com.example.projectcrud.repository.ProjectMentorRepository;
import com.example.projectcrud.response.projectmentor.ProjectMentorResponse;
import com.example.projectcrud.service.ProjectMentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectMentorServiceImpl implements ProjectMentorService {

    @Autowired
    private ProjectMentorRepository projectMentorRepository;

    @Override
    public ProjectMentorResponse createProjectMentor(ProjectMentorCreateDTO projectMentorCreateDTO) {
        ProjectMentor projectMentor = new ProjectMentor();
        // Set properties from DTO to entity
        projectMentor.setName(projectMentorCreateDTO.getName());
        projectMentor.setEmail(projectMentorCreateDTO.getEmail());
        // Save the entity
        projectMentor = projectMentorRepository.save(projectMentor);
        return new ProjectMentorResponse(projectMentor);
    }

    @Override
    public ProjectMentorResponse updateProjectMentor(Long id, ProjectMentorUpdateDTO projectMentorUpdateDTO) {
        Optional<ProjectMentor> optionalProjectMentor = projectMentorRepository.findById(id);
        if (optionalProjectMentor.isPresent()) {
            ProjectMentor projectMentor = optionalProjectMentor.get();
            // Update properties from DTO to entity
            projectMentor.setName(projectMentorUpdateDTO.getName());
            projectMentor.setEmail(projectMentorUpdateDTO.getEmail());
            projectMentor = projectMentorRepository.save(projectMentor);
            return new ProjectMentorResponse(projectMentor);
        }
        throw new ResourceNotFoundException("Project Mentor not found with id " + id);
    }

    @Override
    public void deleteProjectMentor(Long id) {
        if (!projectMentorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project Mentor not found with id " + id);
        }
        projectMentorRepository.deleteById(id);
    }

    @Override
    public List<ProjectMentorResponse> getAllProjectMentors() {
        List<ProjectMentor> projectMentors = projectMentorRepository.findAll();
        return projectMentors.stream()
                .map(ProjectMentorResponse::new)
                .toList();
    }

    @Override
    public ProjectMentorResponse getProjectMentorById(Long id) {
        ProjectMentor projectMentor = projectMentorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project Mentor not found with id " + id));
        return new ProjectMentorResponse(projectMentor);
    }
}