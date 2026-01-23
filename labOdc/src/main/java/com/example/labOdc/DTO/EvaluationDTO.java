package com.example.labOdc.DTO;

import com.example.labOdc.Model.Evaluation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationDTO {

    private String projectId;
    private String evaluatorId;
    private String evaluatedId;
    private Evaluation.EvaluatorType evaluatorType;
    private Evaluation.EvaluatedType evaluatedType;
    private Integer rating;
    private Integer technicalSkills;
    private Integer communication;
    private Integer teamwork;
    private Integer punctuality;
    private String feedback;
    private LocalDate evaluationDate;
    private Boolean isAnonymous;
}
