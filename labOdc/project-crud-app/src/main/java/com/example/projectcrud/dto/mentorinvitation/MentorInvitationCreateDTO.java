package com.example.projectcrud.dto.mentorinvitation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MentorInvitationCreateDTO {

    @NotBlank(message = "Mentor ID is required")
    private String mentorId;

    @NotBlank(message = "Project ID is required")
    private String projectId;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Invitation message is required")
    private String message;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}