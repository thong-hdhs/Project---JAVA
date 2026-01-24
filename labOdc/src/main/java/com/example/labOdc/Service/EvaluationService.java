package com.example.labOdc.Service;

import com.example.labOdc.DTO.EvaluationDTO;
import com.example.labOdc.Model.Evaluation;

import java.util.List;

public interface EvaluationService {

    Evaluation createEvaluation(
            EvaluationDTO dto,
            String evaluatorId,
            Evaluation.EvaluatorType evaluatorType);

    Evaluation getById(String id);

    List<Evaluation> getAll();

    List<Evaluation> getByProject(String projectId);

    List<Evaluation> getByEvaluated(String evaluatedId);

    List<Evaluation> getByEvaluator(String evaluatorId);

    List<Evaluation> getByEvaluatedType(Evaluation.EvaluatedType type);

    Evaluation updateEvaluation(String id, EvaluationDTO dto);

    void deleteEvaluation(String id);
}
