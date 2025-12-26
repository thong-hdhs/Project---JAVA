package com.example.projectcrud.serviceimpl;

import com.example.projectcrud.dto.projectchangerequest.ProjectChangeRequestCreateDTO;
import com.example.projectcrud.dto.projectchangerequest.ProjectChangeRequestUpdateDTO;
import com.example.projectcrud.model.ProjectChangeRequest;
import com.example.projectcrud.repository.ProjectChangeRequestRepository;
import com.example.projectcrud.response.projectchangerequest.ProjectChangeRequestResponse;
import com.example.projectcrud.service.ProjectChangeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectChangeRequestServiceImpl implements ProjectChangeRequestService {

    @Autowired
    private ProjectChangeRequestRepository projectChangeRequestRepository;

    @Override
    public ProjectChangeRequestResponse createProjectChangeRequest(ProjectChangeRequestCreateDTO createDTO) {
        ProjectChangeRequest projectChangeRequest = new ProjectChangeRequest();
        // Set properties from createDTO to projectChangeRequest
        projectChangeRequest = projectChangeRequestRepository.save(projectChangeRequest);
        return new ProjectChangeRequestResponse(projectChangeRequest);
    }

    @Override
    public ProjectChangeRequestResponse updateProjectChangeRequest(Long id, ProjectChangeRequestUpdateDTO updateDTO) {
        Optional<ProjectChangeRequest> optionalRequest = projectChangeRequestRepository.findById(id);
        if (optionalRequest.isPresent()) {
            ProjectChangeRequest projectChangeRequest = optionalRequest.get();
            // Update properties from updateDTO to projectChangeRequest
            projectChangeRequest = projectChangeRequestRepository.save(projectChangeRequest);
            return new ProjectChangeRequestResponse(projectChangeRequest);
        }
        throw new ResourceNotFoundException("Project Change Request not found");
    }

    @Override
    public void deleteProjectChangeRequest(Long id) {
        projectChangeRequestRepository.deleteById(id);
    }

    @Override
    public List<ProjectChangeRequestResponse> getAllProjectChangeRequests() {
        List<ProjectChangeRequest> requests = projectChangeRequestRepository.findAll();
        return requests.stream()
                .map(ProjectChangeRequestResponse::new)
                .toList();
    }

    @Override
    public ProjectChangeRequestResponse getProjectChangeRequestById(Long id) {
        ProjectChangeRequest projectChangeRequest = projectChangeRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project Change Request not found"));
        return new ProjectChangeRequestResponse(projectChangeRequest);
    }
}