package com.example.projectcrud.dto.project;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProjectUpdateDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Long teamId;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}