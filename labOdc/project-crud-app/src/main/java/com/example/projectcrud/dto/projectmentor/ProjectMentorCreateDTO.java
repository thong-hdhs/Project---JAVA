package com.example.projectcrud.dto.projectmentor;

import javax.validation.constraints.NotBlank;

public class ProjectMentorCreateDTO {

    @NotBlank(message = "Mentor ID is required")
    private String mentorId;

    @NotBlank(message = "Project ID is required")
    private String projectId;

    // Getters and Setters

    public String getMentorId() {
        return mentorId;
    }

    public void setMentorId(String mentorId) {
        this.mentorId = mentorId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}