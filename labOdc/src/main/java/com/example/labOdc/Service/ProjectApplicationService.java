package com.example.labOdc.Service;

import java.util.List;

import com.example.labOdc.DTO.ProjectApplicationDTO;
import com.example.labOdc.Model.ProjectApplication;

public interface ProjectApplicationService {
    ProjectApplication createApplication(ProjectApplicationDTO dto);

    List<ProjectApplication> getAllApplications();

    ProjectApplication getApplicationById(String id);

    void deleteApplication(String id);

    ProjectApplication updateApplication(ProjectApplicationDTO dto, String id);

    List<ProjectApplication> findByProjectId(String projectId);

    List<ProjectApplication> findByTalentId(String talentId);
}
