package com.example.projectcrud.serviceimpl;

import com.example.projectcrud.dto.project.ProjectCreateDTO;
import com.example.projectcrud.dto.project.ProjectUpdateDTO;
import com.example.projectcrud.model.Project;
import com.example.projectcrud.repository.ProjectRepository;
import com.example.projectcrud.response.project.ProjectResponse;
import com.example.projectcrud.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ProjectResponse createProject(ProjectCreateDTO projectCreateDTO) {
        Project project = new Project();
        project.setName(projectCreateDTO.getName());
        project.setDescription(projectCreateDTO.getDescription());
        // Set other fields as necessary
        Project savedProject = projectRepository.save(project);
        return new ProjectResponse(savedProject);
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectUpdateDTO projectUpdateDTO) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (!optionalProject.isPresent()) {
            throw new ResourceNotFoundException("Project not found with id: " + id);
        }
        Project project = optionalProject.get();
        project.setName(projectUpdateDTO.getName());
        project.setDescription(projectUpdateDTO.getDescription());
        // Update other fields as necessary
        Project updatedProject = projectRepository.save(project);
        return new ProjectResponse(updatedProject);
    }

    @Override
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }

    @Override
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return new ProjectResponse(project);
    }

    @Override
    public List<ProjectResponse> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream().map(ProjectResponse::new).toList();
    }
}