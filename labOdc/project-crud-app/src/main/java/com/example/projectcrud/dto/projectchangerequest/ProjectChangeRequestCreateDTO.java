package com.example.projectcrud.dto.projectchangerequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProjectChangeRequestCreateDTO {

    @NotBlank(message = "Project ID is required")
    private String projectId;

    @NotBlank(message = "Change request description is required")
    private String description;

    @NotNull(message = "Requested by ID is required")
    private Long requestedById;

    // Getters and Setters

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRequestedById() {
        return requestedById;
    }

    public void setRequestedById(Long requestedById) {
        this.requestedById = requestedById;
    }
}