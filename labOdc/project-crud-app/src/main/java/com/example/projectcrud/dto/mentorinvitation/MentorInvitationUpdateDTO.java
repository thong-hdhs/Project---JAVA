package com.example.projectcrud.dto.mentorinvitation;

import javax.validation.constraints.NotNull;

public class MentorInvitationUpdateDTO {

    @NotNull
    private Long id;

    @NotNull
    private Long projectId;

    @NotNull
    private Long mentorId;

    private String status;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getMentorId() {
        return mentorId;
    }

    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}