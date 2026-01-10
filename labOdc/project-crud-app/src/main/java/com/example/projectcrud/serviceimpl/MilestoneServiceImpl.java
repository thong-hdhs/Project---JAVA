package com.example.projectcrud.serviceimpl;

import com.example.projectcrud.dto.milestone.MilestoneCreateDTO;
import com.example.projectcrud.dto.milestone.MilestoneUpdateDTO;
import com.example.projectcrud.model.Milestone;
import com.example.projectcrud.repository.MilestoneRepository;
import com.example.projectcrud.response.milestone.MilestoneResponse;
import com.example.projectcrud.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MilestoneServiceImpl implements MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Override
    public MilestoneResponse createMilestone(MilestoneCreateDTO milestoneCreateDTO) {
        Milestone milestone = new Milestone();
        milestone.setName(milestoneCreateDTO.getName());
        milestone.setDescription(milestoneCreateDTO.getDescription());
        milestone.setDueDate(milestoneCreateDTO.getDueDate());
        Milestone savedMilestone = milestoneRepository.save(milestone);
        return new MilestoneResponse(savedMilestone);
    }

    @Override
    public MilestoneResponse updateMilestone(Long id, MilestoneUpdateDTO milestoneUpdateDTO) {
        Optional<Milestone> optionalMilestone = milestoneRepository.findById(id);
        if (!optionalMilestone.isPresent()) {
            throw new ResourceNotFoundException("Milestone not found with id: " + id);
        }
        Milestone milestone = optionalMilestone.get();
        milestone.setName(milestoneUpdateDTO.getName());
        milestone.setDescription(milestoneUpdateDTO.getDescription());
        milestone.setDueDate(milestoneUpdateDTO.getDueDate());
        Milestone updatedMilestone = milestoneRepository.save(milestone);
        return new MilestoneResponse(updatedMilestone);
    }

    @Override
    public void deleteMilestone(Long id) {
        if (!milestoneRepository.existsById(id)) {
            throw new ResourceNotFoundException("Milestone not found with id: " + id);
        }
        milestoneRepository.deleteById(id);
    }

    @Override
    public List<MilestoneResponse> getAllMilestones() {
        List<Milestone> milestones = milestoneRepository.findAll();
        return milestones.stream().map(MilestoneResponse::new).toList();
    }

    @Override
    public MilestoneResponse getMilestoneById(Long id) {
        Milestone milestone = milestoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Milestone not found with id: " + id));
        return new MilestoneResponse(milestone);
    }
}