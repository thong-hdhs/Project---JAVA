package com.example.labOdc.Service.Implement;

import com.example.labOdc.DTO.EvaluationDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Evaluation;
import com.example.labOdc.Model.Project;
import com.example.labOdc.Repository.EvaluationRepository;
import com.example.labOdc.Repository.ProjectRepository;
import com.example.labOdc.Service.EvaluationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final ProjectRepository projectRepository;

    @Override
    public Evaluation createEvaluation(
            EvaluationDTO dto,
            String evaluatorId,
            Evaluation.EvaluatorType evaluatorType) {

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Project"));

        Evaluation evaluation = Evaluation.builder()
                .project(project)
                .evaluatorId(evaluatorId)
                .evaluatedId(dto.getEvaluatedId())
                .evaluatorType(evaluatorType)
                .evaluatedType(dto.getEvaluatedType())
                .rating(dto.getRating())
                .technicalSkills(dto.getTechnicalSkills())
                .communication(dto.getCommunication())
                .teamwork(dto.getTeamwork())
                .punctuality(dto.getPunctuality())
                .feedback(dto.getFeedback())
                .isAnonymous(dto.getIsAnonymous() != null ? dto.getIsAnonymous() : false)
                .evaluationDate(LocalDate.now())
                .build();

        return evaluationRepository.save(evaluation);
    }

    @Override
    public Evaluation getById(String id) {
        return evaluationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Evaluation"));
    }

    @Override
    public List<Evaluation> getAll() {
        return evaluationRepository.findAll();
    }

    @Override
    public List<Evaluation> getByProject(String projectId) {
        return evaluationRepository.findByProjectId(projectId);
    }

    @Override
    public List<Evaluation> getByEvaluated(String evaluatedId) {
        return evaluationRepository.findByEvaluatedId(evaluatedId);
    }

    @Override
    public List<Evaluation> getByEvaluator(String evaluatorId) {
        return evaluationRepository.findByEvaluatorId(evaluatorId);
    }

    @Override
    public List<Evaluation> getByEvaluatedType(Evaluation.EvaluatedType type) {
        return evaluationRepository.findByEvaluatedType(type);
    }

    @Override
    public Evaluation updateEvaluation(String id, EvaluationDTO dto) {

        Evaluation evaluation = getById(id);

        if (dto.getRating() != null) {
            evaluation.setRating(dto.getRating());
        }
        if (dto.getTechnicalSkills() != null) {
            evaluation.setTechnicalSkills(dto.getTechnicalSkills());
        }
        if (dto.getCommunication() != null) {
            evaluation.setCommunication(dto.getCommunication());
        }
        if (dto.getTeamwork() != null) {
            evaluation.setTeamwork(dto.getTeamwork());
        }
        if (dto.getPunctuality() != null) {
            evaluation.setPunctuality(dto.getPunctuality());
        }
        if (dto.getFeedback() != null) {
            evaluation.setFeedback(dto.getFeedback());
        }
        if (dto.getIsAnonymous() != null) {
            evaluation.setIsAnonymous(dto.getIsAnonymous());
        }

        return evaluationRepository.save(evaluation);
    }

    @Override
    public void deleteEvaluation(String id) {
        evaluationRepository.deleteById(id);
    }
}