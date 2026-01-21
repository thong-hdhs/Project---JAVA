package com.example.labOdc.Service.Implement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.ProjectDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Company;
import com.example.labOdc.Model.LabAdmin;
import com.example.labOdc.Model.Mentor;
import com.example.labOdc.Model.Project;
import com.example.labOdc.Model.ProjectStatus;
import com.example.labOdc.Model.ValidationStatus;
import com.example.labOdc.Repository.CompanyRepository;
import com.example.labOdc.Repository.LabAdminRepository;
import com.example.labOdc.Repository.MentorRepository;
import com.example.labOdc.Repository.ProjectRepository;
import com.example.labOdc.Service.ProjectService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final MentorRepository mentorRepository;
    private final LabAdminRepository labAdminRepository;

    @Override
    public Project createProject(ProjectDTO dto) {
        if (dto.getProjectCode() != null && !dto.getProjectCode().isBlank()) {
            if (projectRepository.existsByProjectCode(dto.getProjectCode())) {
                throw new IllegalArgumentException("projectCode already exists");
            }
        }

        if (dto.getCompanyId() == null || dto.getCompanyId().isBlank()) {
            throw new IllegalArgumentException("companyId is required");
        }

        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", dto.getCompanyId()));

        Mentor mentor = null;
        if (dto.getMentorId() != null && !dto.getMentorId().isBlank()) {
            mentor = mentorRepository.findById(dto.getMentorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Mentor", "id", dto.getMentorId()));
        }

        Project project = Project.builder()
                .company(company)
                .mentor(mentor)
                .projectName(dto.getProjectName())
                .projectCode(dto.getProjectCode())
                .description(dto.getDescription())
                .requirements(dto.getRequirements())
                .budget(dto.getBudget())
                .durationMonths(dto.getDurationMonths())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .actualEndDate(dto.getActualEndDate())
                // status + validationStatus để default theo entity
                .maxTeamSize(dto.getMaxTeamSize() != null ? dto.getMaxTeamSize() : 5)
                .requiredSkills(dto.getRequiredSkills())
                .build();

        return projectRepository.save(project);
    }

    @Override
    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(String id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
    }

    @Override
    public Project updateProject(ProjectDTO dto, String id) {
        Project project = getProjectById(id);

        if (!(project.getStatus() == ProjectStatus.DRAFT
                || project.getValidationStatus() == ValidationStatus.REJECTED)) {
            throw new IllegalStateException("Only DRAFT or REJECTED projects can be edited");
        }

        if (dto.getProjectCode() != null && !dto.getProjectCode().isBlank()) {
            if (!dto.getProjectCode().equals(project.getProjectCode())
                    && projectRepository.existsByProjectCode(dto.getProjectCode())) {
                throw new IllegalArgumentException("projectCode already exists");
            }
            project.setProjectCode(dto.getProjectCode());
        }

        if (dto.getCompanyId() != null && !dto.getCompanyId().isBlank()) {
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company", "id", dto.getCompanyId()));
            project.setCompany(company);
        }

        if (dto.getMentorId() != null) {
            if (dto.getMentorId().isBlank()) {
                project.setMentor(null);
            } else {
                Mentor mentor = mentorRepository.findById(dto.getMentorId())
                        .orElseThrow(() -> new ResourceNotFoundException("Mentor", "id", dto.getMentorId()));
                project.setMentor(mentor);
            }
        }

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

        if (dto.getMaxTeamSize() != null)
            project.setMaxTeamSize(dto.getMaxTeamSize());
        if (dto.getRequiredSkills() != null)
            project.setRequiredSkills(dto.getRequiredSkills());

        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }

    // ---------- workflow ----------

    @Override
    public Project submitProject(String projectId) {
        Project project = getProjectById(projectId);

        if (project.getStatus() != ProjectStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT project can be submitted");
        }

        project.setStatus(ProjectStatus.SUBMITTED);
        project.setValidationStatus(ValidationStatus.PENDING);
        project.setRejectionReason(null);

        return projectRepository.save(project);
    }

    @Override
    public Project approveProject(String projectId, String validatedById) {
        Project project = getProjectById(projectId);

        if (project.getStatus() != ProjectStatus.SUBMITTED) {
            throw new IllegalStateException("Project must be SUBMITTED before approval");
        }

        if (validatedById == null || validatedById.isBlank()) {
            throw new IllegalArgumentException("validatedBy is required");
        }

        LabAdmin validatedBy = labAdminRepository.findById(validatedById)
                .orElseThrow(() -> new ResourceNotFoundException("LabAdmin", "id", validatedById));

        project.setValidationStatus(ValidationStatus.APPROVED);
        project.setValidatedBy(validatedBy);
        project.setValidatedAt(LocalDateTime.now());
        project.setRejectionReason(null);

        project.setStatus(ProjectStatus.IN_PROGRESS);

        return projectRepository.save(project);
    }

    @Override
    public Project rejectProject(String projectId, String validatedById, String rejectionReason) {
        Project project = getProjectById(projectId);

        if (project.getStatus() != ProjectStatus.SUBMITTED) {
            throw new IllegalStateException("Project must be SUBMITTED before rejection");
        }
        if (rejectionReason == null || rejectionReason.isBlank()) {
            throw new IllegalArgumentException("rejectionReason is required");
        }
        if (validatedById == null || validatedById.isBlank()) {
            throw new IllegalArgumentException("validatedBy is required");
        }

        LabAdmin validatedBy = labAdminRepository.findById(validatedById)
                .orElseThrow(() -> new ResourceNotFoundException("LabAdmin", "id", validatedById));

        project.setValidationStatus(ValidationStatus.REJECTED);
        project.setValidatedBy(validatedBy);
        project.setValidatedAt(LocalDateTime.now());
        project.setRejectionReason(rejectionReason);

        project.setStatus(ProjectStatus.DRAFT);

        return projectRepository.save(project);
    }

    @Override
    public Project updateProjectStatus(String projectId, ProjectStatus status) {
        Project project = getProjectById(projectId);

        if (status == null)
            throw new IllegalArgumentException("status is required");

        project.setStatus(status);

        return projectRepository.save(project);
    }
}