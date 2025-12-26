package com.example.projectcrud.dto.projectteam;

import javax.validation.constraints.NotBlank;

public class ProjectTeamCreateDTO {

    @NotBlank(message = "Team name is required")
    private String teamName;

    @NotBlank(message = "Project ID is required")
    private String projectId;

    // Getters and Setters

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}