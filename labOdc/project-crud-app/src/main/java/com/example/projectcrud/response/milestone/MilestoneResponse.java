package com.example.projectcrud.response.milestone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilestoneResponse {
    private Long id;
    private String name;
    private String description;
    private String dueDate;
    private String status;
}