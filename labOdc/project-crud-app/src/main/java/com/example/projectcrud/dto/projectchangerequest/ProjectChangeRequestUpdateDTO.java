package com.example.projectcrud.dto.projectchangerequest;

import javax.validation.constraints.NotNull;

public class ProjectChangeRequestUpdateDTO {

    @NotNull
    private Long id;

    @NotNull
    private String description;

    @NotNull
    private String status;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}