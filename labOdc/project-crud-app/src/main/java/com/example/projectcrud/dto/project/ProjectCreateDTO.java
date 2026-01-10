package com.example.projectcrud.dto.project;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProjectCreateDTO {

    @NotBlank(message = "Project name is mandatory")
    private String name;

    @NotNull(message = "Start date is mandatory")
    private String startDate;

    @NotNull(message = "End date is mandatory")
    private String endDate;

    private String description;

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}