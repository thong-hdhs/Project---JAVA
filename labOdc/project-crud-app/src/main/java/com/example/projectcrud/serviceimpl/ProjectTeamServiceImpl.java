package com.example.projectcrud.serviceimpl;

import com.example.projectcrud.dto.projectteam.ProjectTeamCreateDTO;
import com.example.projectcrud.dto.projectteam.ProjectTeamUpdateDTO;
import com.example.projectcrud.model.ProjectTeam;
import com.example.projectcrud.repository.ProjectTeamRepository;
import com.example.projectcrud.response.projectteam.ProjectTeamResponse;
import com.example.projectcrud.service.ProjectTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectTeamServiceImpl implements ProjectTeamService {

    @Autowired
    private ProjectTeamRepository projectTeamRepository;

    @Override
    public ProjectTeamResponse createProjectTeam(ProjectTeamCreateDTO projectTeamCreateDTO) {
        ProjectTeam projectTeam = new ProjectTeam();
        // Set properties from DTO to entity
        projectTeam.setName(projectTeamCreateDTO.getName());
        projectTeam.setDescription(projectTeamCreateDTO.getDescription());
        // Save the entity
        projectTeam = projectTeamRepository.save(projectTeam);
        return new ProjectTeamResponse(projectTeam);
    }

    @Override
    public ProjectTeamResponse updateProjectTeam(Long id, ProjectTeamUpdateDTO projectTeamUpdateDTO) {
        Optional<ProjectTeam> optionalProjectTeam = projectTeamRepository.findById(id);
        if (optionalProjectTeam.isPresent()) {
            ProjectTeam projectTeam = optionalProjectTeam.get();
            // Update properties from DTO to entity
            projectTeam.setName(projectTeamUpdateDTO.getName());
            projectTeam.setDescription(projectTeamUpdateDTO.getDescription());
            // Save the updated entity
            projectTeam = projectTeamRepository.save(projectTeam);
            return new ProjectTeamResponse(projectTeam);
        }
        throw new ResourceNotFoundException("Project Team not found with id " + id);
    }

    @Override
    public void deleteProjectTeam(Long id) {
        if (!projectTeamRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project Team not found with id " + id);
        }
        projectTeamRepository.deleteById(id);
    }

    @Override
    public ProjectTeamResponse getProjectTeamById(Long id) {
        ProjectTeam projectTeam = projectTeamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project Team not found with id " + id));
        return new ProjectTeamResponse(projectTeam);
    }

    @Override
    public List<ProjectTeamResponse> getAllProjectTeams() {
        List<ProjectTeam> projectTeams = projectTeamRepository.findAll();
        return projectTeams.stream()
                .map(ProjectTeamResponse::new)
                .toList();
    }
}