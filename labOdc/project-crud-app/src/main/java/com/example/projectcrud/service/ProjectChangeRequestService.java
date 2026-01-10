package com.example.projectcrud.service;

import com.example.projectcrud.dto.projectchangerequest.ProjectChangeRequestCreateDTO;
import com.example.projectcrud.dto.projectchangerequest.ProjectChangeRequestUpdateDTO;
import com.example.projectcrud.model.ProjectChangeRequest;
import java.util.List;

public interface ProjectChangeRequestService {
    
    ProjectChangeRequest createProjectChangeRequest(ProjectChangeRequestCreateDTO createDTO);
    
    ProjectChangeRequest updateProjectChangeRequest(Long id, ProjectChangeRequestUpdateDTO updateDTO);
    
    ProjectChangeRequest getProjectChangeRequestById(Long id);
    
    List<ProjectChangeRequest> getAllProjectChangeRequests();
    
    void deleteProjectChangeRequest(Long id);
}