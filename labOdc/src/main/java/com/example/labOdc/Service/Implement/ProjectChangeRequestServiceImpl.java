package com.example.labOdc.Service.Implement;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.labOdc.DTO.ProjectChangeRequestDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Project;
import com.example.labOdc.Model.ProjectChangeRequest;
import com.example.labOdc.Model.ProjectChangeRequestStatus;
import com.example.labOdc.Model.ProjectChangeRequestType;
import com.example.labOdc.Model.ProjectStatus;
import com.example.labOdc.Model.User;
import com.example.labOdc.Repository.ProjectChangeRequestRepository;
import com.example.labOdc.Repository.ProjectRepository;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.ProjectChangeRequestService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectChangeRequestServiceImpl implements ProjectChangeRequestService {

    private final ProjectChangeRequestRepository projectChangeRequestRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    private User getCurrentUserOrThrow() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null || auth.getName().isBlank()) {
            throw new IllegalStateException("Unauthenticated request");
        }

        String username = auth.getName(); // = JWT subject = user.username
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }

    @Override
    public ProjectChangeRequest createProjectChangeRequest(ProjectChangeRequestDTO dto) {
        if (dto.getProjectId() == null || dto.getProjectId().isBlank())
            throw new IllegalArgumentException("projectId is required");
        if (dto.getRequestType() == null)
            throw new IllegalArgumentException("requestType is required");

        User requestedBy = getCurrentUserOrThrow();

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", dto.getProjectId()));

        long pendingCount = projectChangeRequestRepository.countByProjectIdAndStatus(
                dto.getProjectId(), ProjectChangeRequestStatus.PENDING);
        if (pendingCount > 0) {
            throw new IllegalStateException("This project already has a PENDING change request");
        }

        ProjectChangeRequest pcr = ProjectChangeRequest.builder()
                .project(project)
                .requestedBy(requestedBy)
                .requestType(dto.getRequestType())
                .reason(dto.getReason())
                .proposedChanges(dto.getProposedChanges())
                .impactAnalysis(dto.getImpactAnalysis())
                .status(ProjectChangeRequestStatus.PENDING)
                .requestedDate(LocalDate.now())
                .reviewedDate(null)
                .reviewNotes(null)
                .approvedBy(null)
                .build();

        return projectChangeRequestRepository.save(pcr);
    }

    @Override
    public List<ProjectChangeRequest> getAllProjectChangeRequest() {
        return projectChangeRequestRepository.findAll();
    }

    @Override
    public ProjectChangeRequest getProjectChangeRequestById(String id) {
        return projectChangeRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProjectChangeRequest", "id", id));
    }

    @Override
    public ProjectChangeRequest updateProjectChangeRequest(ProjectChangeRequestDTO dto, String id) {
        ProjectChangeRequest pcr = getProjectChangeRequestById(id);

        if (pcr.getStatus() != ProjectChangeRequestStatus.PENDING) {
            throw new IllegalStateException("Only PENDING change request can be edited");
        }

        // (tuỳ bạn) enforce: chỉ người tạo mới được sửa
        User current = getCurrentUserOrThrow();
        if (pcr.getRequestedBy() != null && current.getId() != null
                && !current.getId().equals(pcr.getRequestedBy().getId())) {
            throw new IllegalStateException("Only the requester can edit this change request");
        }

        if (dto.getReason() != null)
            pcr.setReason(dto.getReason());
        if (dto.getProposedChanges() != null)
            pcr.setProposedChanges(dto.getProposedChanges());
        if (dto.getImpactAnalysis() != null)
            pcr.setImpactAnalysis(dto.getImpactAnalysis());

        return projectChangeRequestRepository.save(pcr);
    }

    @Override
    public void deleteProjectChangeRequest(String id) {
        projectChangeRequestRepository.deleteById(id);
    }

    @Override
    public List<ProjectChangeRequest> getByProjectId(String projectId) {
        return projectChangeRequestRepository.findByProjectIdOrderByCreatedAtDesc(projectId);
    }

    // ===== workflow using JWT current user =====

    @Override
    @Transactional
    public ProjectChangeRequest approve(String id, String reviewNotes) {
        ProjectChangeRequest pcr = getProjectChangeRequestById(id);

        if (pcr.getStatus() != ProjectChangeRequestStatus.PENDING) {
            throw new IllegalStateException("Only PENDING change request can be approved");
        }

        User approver = getCurrentUserOrThrow();

        pcr.setStatus(ProjectChangeRequestStatus.APPROVED);
        pcr.setApprovedBy(approver);
        pcr.setReviewedDate(LocalDate.now());
        pcr.setReviewNotes(reviewNotes);

        projectChangeRequestRepository.save(pcr);

        if (pcr.getRequestType() == ProjectChangeRequestType.CANCELLATION) {
            Project project = pcr.getProject();
            if (project == null) {
                throw new IllegalStateException("Change request is missing project");
            }
            project.setStatus(ProjectStatus.CANCELLED);
            projectRepository.save(project);
        }

        return pcr;
    }

    @Override
    public ProjectChangeRequest reject(String id, String reviewNotes) {
        if (reviewNotes == null || reviewNotes.isBlank()) {
            throw new IllegalArgumentException("reviewNotes is required when rejecting");
        }

        ProjectChangeRequest pcr = getProjectChangeRequestById(id);

        if (pcr.getStatus() != ProjectChangeRequestStatus.PENDING) {
            throw new IllegalStateException("Only PENDING change request can be rejected");
        }

        User approver = getCurrentUserOrThrow();

        pcr.setStatus(ProjectChangeRequestStatus.REJECTED);
        pcr.setApprovedBy(approver);
        pcr.setReviewedDate(LocalDate.now());
        pcr.setReviewNotes(reviewNotes);

        return projectChangeRequestRepository.save(pcr);
    }

    @Override
    public ProjectChangeRequest cancel(String id) {
        ProjectChangeRequest pcr = getProjectChangeRequestById(id);

        if (pcr.getStatus() != ProjectChangeRequestStatus.PENDING) {
            throw new IllegalStateException("Only PENDING change request can be cancelled");
        }

        // (tuỳ bạn) enforce: chỉ người tạo mới được cancel
        User current = getCurrentUserOrThrow();
        if (pcr.getRequestedBy() != null && current.getId() != null
                && !current.getId().equals(pcr.getRequestedBy().getId())) {
            throw new IllegalStateException("Only the requester can cancel this change request");
        }

        pcr.setStatus(ProjectChangeRequestStatus.CANCELLED);
        pcr.setReviewedDate(LocalDate.now());

        return projectChangeRequestRepository.save(pcr);
    }
}