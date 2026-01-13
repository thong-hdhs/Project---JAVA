package com.example.labOdc.Repository;

import com.example.labOdc.Model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, String> {

    List<Evaluation> findByProjectId(String projectId);

    List<Evaluation> findByEvaluatorId(String evaluatorId);

    List<Evaluation> findByEvaluatedId(String evaluatedId);

    List<Evaluation> findByEvaluatedType(Evaluation.EvaluatedType evaluatedType);
}
