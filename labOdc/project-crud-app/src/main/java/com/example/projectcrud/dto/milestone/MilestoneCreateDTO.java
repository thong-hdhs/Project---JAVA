package com.example.projectcrud.dto.milestone;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class MilestoneCreateDTO {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotNull(message = "Due date is mandatory")
    private LocalDateTime dueDate;

    @NotBlank(message = "Description is mandatory")
    private String description;

    // Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}