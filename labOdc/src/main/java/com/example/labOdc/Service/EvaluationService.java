package com.example.labOdc.Service;

import com.example.labOdc.DTO.EvaluationDTO;
import com.example.labOdc.Model.Evaluation;

import java.util.List;

public interface EvaluationService {

    Evaluation create(EvaluationDTO evaluationDTO);

    List<Evaluation> getAll();

    Evaluation getById(String id);

    List<Evaluation> getByProject(String projectId);

    List<Evaluation> getByEvaluator(String evaluatorId);

    List<Evaluation> getByEvaluated(String evaluatedId);

    Evaluation update(String id, EvaluationDTO evaluationDTO);

    void delete(String id);
}
