package com.example.labOdc.Service.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.ProjectApplicationDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Project;
import com.example.labOdc.Model.ProjectApplication;
import com.example.labOdc.Model.Talent;
import com.example.labOdc.Model.User;
import com.example.labOdc.Repository.ProjectApplicationRepository;
import com.example.labOdc.Repository.ProjectRepository;
import com.example.labOdc.Repository.TalentRepository;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.ProjectApplicationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectApplicationServiceImpl implements ProjectApplicationService {

    private final ProjectApplicationRepository applicationRepository;
    private final ProjectRepository projectRepository;
    private final TalentRepository talentRepository;
    private final UserRepository userRepository;

    @Override
    public ProjectApplication createApplication(ProjectApplicationDTO dto) {
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        Talent talent = talentRepository.findById(dto.getTalentId())
                .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));

        if (applicationRepository.existsByProjectIdAndTalentId(project.getId(), talent.getId())) {
            throw new IllegalArgumentException("Application already exists for this project and talent");
        }

        ProjectApplication pa = ProjectApplication.builder()
                .project(project)
                .talent(talent)
                .coverLetter(dto.getCoverLetter())
                .status(dto.getStatus() != null ? dto.getStatus() : ProjectApplication.Status.PENDING)
                .rejectionReason(dto.getRejectionReason())
                .appliedAt(dto.getAppliedAt())
                .build();

        if (dto.getReviewedById() != null) {
            User reviewer = userRepository.findById(dto.getReviewedById())
                    .orElseThrow(() -> new ResourceNotFoundException("Reviewer not found"));
            pa.setReviewedBy(reviewer);
            pa.setReviewedAt(dto.getReviewedAt());
        }

        applicationRepository.save(pa);
        return pa;
    }

    @Override
    public List<ProjectApplication> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public ProjectApplication getApplicationById(String id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
    }

    @Override
    public void deleteApplication(String id) {
        applicationRepository.deleteById(id);
    }

    @Override
    public ProjectApplication updateApplication(ProjectApplicationDTO dto, String id) {
        ProjectApplication pa = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        if (dto.getCoverLetter() != null) pa.setCoverLetter(dto.getCoverLetter());
        if (dto.getStatus() != null) pa.setStatus(dto.getStatus());
        if (dto.getRejectionReason() != null) pa.setRejectionReason(dto.getRejectionReason());
        if (dto.getReviewedById() != null) {
            User reviewer = userRepository.findById(dto.getReviewedById())
                    .orElseThrow(() -> new ResourceNotFoundException("Reviewer not found"));
            pa.setReviewedBy(reviewer);
            pa.setReviewedAt(dto.getReviewedAt());
        }

        applicationRepository.save(pa);
        return pa;
    }

    @Override
    public List<ProjectApplication> findByProjectId(String projectId) {
        return applicationRepository.findByProjectId(projectId);
    }

    @Override
    public List<ProjectApplication> findByTalentId(String talentId) {
        return applicationRepository.findByTalentId(talentId);
    }
}
