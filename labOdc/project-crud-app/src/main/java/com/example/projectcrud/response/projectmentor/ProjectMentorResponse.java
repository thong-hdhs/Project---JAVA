package com.example.projectcrud.response.projectmentor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMentorResponse {
    private Long id;
    private String mentorName;
    private String projectName;
    private String role;
    private String status;
}