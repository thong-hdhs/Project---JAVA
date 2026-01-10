package com.example.labOdc.Service;

import java.util.List;

import com.example.labOdc.DTO.ProjectChangeRequestDTO;
import com.example.labOdc.Model.ProjectChangeRequest;

public interface ProjectChangeRequestService {
    ProjectChangeRequest createProjectChangeRequest(ProjectChangeRequestDTO dto);

    List<ProjectChangeRequest> getAllProjectChangeRequest();

    ProjectChangeRequest getProjectChangeRequestById(String id);

    ProjectChangeRequest updateProjectChangeRequest(ProjectChangeRequestDTO dto, String id);

    void deleteProjectChangeRequest(String id);
}