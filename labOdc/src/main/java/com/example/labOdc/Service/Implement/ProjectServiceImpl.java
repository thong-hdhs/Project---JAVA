package com.example.labOdc.Service.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.ProjectDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Project;
import com.example.labOdc.Repository.ProjectRepository;
import com.example.labOdc.Service.ProjectService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Project createProject(ProjectDTO dto) {
        if (dto.getProjectCode() != null && !dto.getProjectCode().isBlank()) {
            if (projectRepository.existsByProjectCode(dto.getProjectCode())) {
                throw new IllegalArgumentException("projectCode already exists");
            }
        }

        Project project = Project.builder()
                .companyId(dto.getCompanyId())
                .mentorId(dto.getMentorId())
                .projectName(dto.getProjectName())
                .projectCode(dto.getProjectCode())
                .description(dto.getDescription())
                .requirements(dto.getRequirements())
                .budget(dto.getBudget())
                .durationMonths(dto.getDurationMonths())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .actualEndDate(dto.getActualEndDate())
                .status(dto.getStatus())
                .validationStatus(dto.getValidationStatus())
                .validatedBy(dto.getValidatedBy())
                .validatedAt(dto.getValidatedAt())
                .rejectionReason(dto.getRejectionReason())
                .maxTeamSize(dto.getMaxTeamSize() != null ? dto.getMaxTeamSize() : 5)
                .requiredSkills(dto.getRequiredSkills())
                .build();

        projectRepository.save(project);
        return project;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(String id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
    }

    @Override
    public Project updateProject(ProjectDTO dto, String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));

        if (dto.getProjectCode() != null && !dto.getProjectCode().isBlank()) {
            if (!dto.getProjectCode().equals(project.getProjectCode())
                    && projectRepository.existsByProjectCode(dto.getProjectCode())) {
                throw new IllegalArgumentException("projectCode already exists");
            }
            project.setProjectCode(dto.getProjectCode());
        }

        if (dto.getCompanyId() != null)
            project.setCompanyId(dto.getCompanyId());
        if (dto.getMentorId() != null)
            project.setMentorId(dto.getMentorId());
        if (dto.getProjectName() != null)
            project.setProjectName(dto.getProjectName());
        if (dto.getDescription() != null)
            project.setDescription(dto.getDescription());
        if (dto.getRequirements() != null)
            project.setRequirements(dto.getRequirements());

        if (dto.getBudget() != null)
            project.setBudget(dto.getBudget());
        if (dto.getDurationMonths() != null)
            project.setDurationMonths(dto.getDurationMonths());
        if (dto.getStartDate() != null)
            project.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null)
            project.setEndDate(dto.getEndDate());
        if (dto.getActualEndDate() != null)
            project.setActualEndDate(dto.getActualEndDate());

        if (dto.getStatus() != null)
            project.setStatus(dto.getStatus());
        if (dto.getValidationStatus() != null)
            project.setValidationStatus(dto.getValidationStatus());
        if (dto.getValidatedBy() != null)
            project.setValidatedBy(dto.getValidatedBy());
        if (dto.getValidatedAt() != null)
            project.setValidatedAt(dto.getValidatedAt());
        if (dto.getRejectionReason() != null)
            project.setRejectionReason(dto.getRejectionReason());

        if (dto.getMaxTeamSize() != null)
            project.setMaxTeamSize(dto.getMaxTeamSize());
        if (dto.getRequiredSkills() != null)
            project.setRequiredSkills(dto.getRequiredSkills());

        projectRepository.save(project);
        return project;
    }

    @Override
    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }
}