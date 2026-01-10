package com.example.labOdc.Service.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.MilestoneDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Milestone;
import com.example.labOdc.Repository.MilestoneRepository;
import com.example.labOdc.Service.MilestoneService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {

    private final MilestoneRepository milestoneRepository;

    @Override
    public Milestone createMilestone(MilestoneDTO dto) {
        Milestone m = Milestone.builder()
                .projectId(dto.getProjectId())
                .milestoneName(dto.getMilestoneName())
                .description(dto.getDescription())
                .dueDate(dto.getDueDate())
                .completedDate(dto.getCompletedDate())
                .status(dto.getStatus()) // null -> default PENDING
                .paymentPercentage(dto.getPaymentPercentage())
                .deliverables(dto.getDeliverables())
                .build();

        milestoneRepository.save(m);
        return m;
    }

    @Override
    public List<Milestone> getAllMilestone() {
        return milestoneRepository.findAll();
    }

    @Override
    public Milestone getMilestoneById(String id) {
        return milestoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Milestone", "id", id));
    }

    @Override
    public Milestone updateMilestone(MilestoneDTO dto, String id) {
        Milestone m = milestoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Milestone", "id", id));

        if (dto.getProjectId() != null)
            m.setProjectId(dto.getProjectId());
        if (dto.getMilestoneName() != null)
            m.setMilestoneName(dto.getMilestoneName());
        if (dto.getDescription() != null)
            m.setDescription(dto.getDescription());
        if (dto.getDueDate() != null)
            m.setDueDate(dto.getDueDate());
        if (dto.getCompletedDate() != null)
            m.setCompletedDate(dto.getCompletedDate());
        if (dto.getStatus() != null)
            m.setStatus(dto.getStatus());
        if (dto.getPaymentPercentage() != null)
            m.setPaymentPercentage(dto.getPaymentPercentage());
        if (dto.getDeliverables() != null)
            m.setDeliverables(dto.getDeliverables());

        milestoneRepository.save(m);
        return m;
    }

    @Override
    public void deleteMilestone(String id) {
        milestoneRepository.deleteById(id);
    }
}