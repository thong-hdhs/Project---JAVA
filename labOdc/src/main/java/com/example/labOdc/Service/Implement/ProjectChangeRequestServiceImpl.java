package com.example.labOdc.Service.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.ProjectChangeRequestDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.ProjectChangeRequest;
import com.example.labOdc.Repository.ProjectChangeRequestRepository;
import com.example.labOdc.Service.ProjectChangeRequestService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectChangeRequestServiceImpl implements ProjectChangeRequestService {

    private final ProjectChangeRequestRepository projectChangeRequestRepository;

    @Override
    public ProjectChangeRequest createProjectChangeRequest(ProjectChangeRequestDTO dto) {
        ProjectChangeRequest pcr = ProjectChangeRequest.builder()
                .projectId(dto.getProjectId())
                .requestedBy(dto.getRequestedBy())
                .requestType(dto.getRequestType())
                .reason(dto.getReason())
                .proposedChanges(dto.getProposedChanges())
                .impactAnalysis(dto.getImpactAnalysis())
                .status(dto.getStatus()) // null -> default PENDING
                .approvedBy(dto.getApprovedBy())
                .requestedDate(dto.getRequestedDate())
                .reviewedDate(dto.getReviewedDate())
                .reviewNotes(dto.getReviewNotes())
                .build();

        projectChangeRequestRepository.save(pcr);
        return pcr;
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
        ProjectChangeRequest pcr = projectChangeRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProjectChangeRequest", "id", id));

        if (dto.getProjectId() != null)
            pcr.setProjectId(dto.getProjectId());
        if (dto.getRequestedBy() != null)
            pcr.setRequestedBy(dto.getRequestedBy());
        if (dto.getRequestType() != null)
            pcr.setRequestType(dto.getRequestType());

        if (dto.getReason() != null)
            pcr.setReason(dto.getReason());
        if (dto.getProposedChanges() != null)
            pcr.setProposedChanges(dto.getProposedChanges());
        if (dto.getImpactAnalysis() != null)
            pcr.setImpactAnalysis(dto.getImpactAnalysis());

        if (dto.getStatus() != null)
            pcr.setStatus(dto.getStatus());
        if (dto.getApprovedBy() != null)
            pcr.setApprovedBy(dto.getApprovedBy());

        if (dto.getRequestedDate() != null)
            pcr.setRequestedDate(dto.getRequestedDate());
        if (dto.getReviewedDate() != null)
            pcr.setReviewedDate(dto.getReviewedDate());
        if (dto.getReviewNotes() != null)
            pcr.setReviewNotes(dto.getReviewNotes());

        projectChangeRequestRepository.save(pcr);
        return pcr;
    }

    @Override
    public void deleteProjectChangeRequest(String id) {
        projectChangeRequestRepository.deleteById(id);
    }
}