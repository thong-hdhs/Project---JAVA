package com.example.projectcrud.response.projectchangerequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectChangeRequestResponse {
    private Long id;
    private String projectName;
    private String changeDescription;
    private String status;
    private String createdDate;
    private String updatedDate;
}