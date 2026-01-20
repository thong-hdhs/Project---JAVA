package com.example.labOdc.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectTeamDTO {
    @NotBlank
    private String projectId;
}