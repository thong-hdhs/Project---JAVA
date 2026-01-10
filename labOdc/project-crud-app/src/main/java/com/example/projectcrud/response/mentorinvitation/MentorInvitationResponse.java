package com.example.projectcrud.response.mentorinvitation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MentorInvitationResponse {
    private Long id;
    private String mentorEmail;
    private String projectName;
    private String status;
    private String createdDate;
    private String updatedDate;
}