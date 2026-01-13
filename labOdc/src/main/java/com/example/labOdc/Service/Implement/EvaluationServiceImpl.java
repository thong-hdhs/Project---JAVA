package com.example.labOdc.Service.Implement;

import com.example.labOdc.DTO.EvaluationDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Evaluation;
import com.example.labOdc.Repository.EvaluationRepository;
import com.example.labOdc.Service.EvaluationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {

    private final EvaluationRepository repository;

    @Override
    public Evaluation create(EvaluationDTO evaluationDTO) {

        Evaluation evaluation = Evaluation.builder()
                .projectId(evaluationDTO.getProjectId())
                .evaluatorId(evaluationDTO.getEvaluatorId())
                .evaluatedId(evaluationDTO.getEvaluatedId())
                .evaluatorType(evaluationDTO.getEvaluatorType())
                .evaluatedType(evaluationDTO.getEvaluatedType())
                .rating(evaluationDTO.getRating())
                .technicalSkills(evaluationDTO.getTechnicalSkills())
                .communication(evaluationDTO.getCommunication())
                .teamwork(evaluationDTO.getTeamwork())
                .punctuality(evaluationDTO.getPunctuality())
                .feedback(evaluationDTO.getFeedback())
                .evaluationDate(
                        evaluationDTO.getEvaluationDate() != null
                                ? evaluationDTO.getEvaluationDate()
                                : LocalDate.now()
                )
                .isAnonymous(evaluationDTO.getIsAnonymous() != null && evaluationDTO.getIsAnonymous())
                .build();

        return repository.save(evaluation);
    }

    @Override
    public List<Evaluation> getAll() {
        return repository.findAll();
    }

    @Override
    public Evaluation getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy evaluation"));
    }

    @Override
    public List<Evaluation> getByProject(String projectId) {
        return repository.findByProjectId(projectId);
    }

    @Override
    public List<Evaluation> getByEvaluator(String evaluatorId) {
        return repository.findByEvaluatorId(evaluatorId);
    }

    @Override
    public List<Evaluation> getByEvaluated(String evaluatedId) {
        return repository.findByEvaluatedId(evaluatedId);
    }

    @Override
    public Evaluation update(String id, EvaluationDTO evaluationDTO) {
        Evaluation evaluation = getById(id);

        if (evaluationDTO.getRating() != null)
            evaluation.setRating(evaluationDTO.getRating());
        if (evaluationDTO.getTechnicalSkills() != null)
            evaluation.setTechnicalSkills(evaluationDTO.getTechnicalSkills());
        if (evaluationDTO.getCommunication() != null)
            evaluation.setCommunication(evaluationDTO.getCommunication());
        if (evaluationDTO.getTeamwork() != null)
            evaluation.setTeamwork(evaluationDTO.getTeamwork());
        if (evaluationDTO.getPunctuality() != null)
            evaluation.setPunctuality(evaluationDTO.getPunctuality());
        if (evaluationDTO.getFeedback() != null)
            evaluation.setFeedback(evaluationDTO.getFeedback());

        return repository.save(evaluation);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
