package com.example.projectcrud.service;

import com.example.projectcrud.dto.milestone.MilestoneCreateDTO;
import com.example.projectcrud.dto.milestone.MilestoneUpdateDTO;
import com.example.projectcrud.model.Milestone;
import com.example.projectcrud.response.milestone.MilestoneResponse;

import java.util.List;

public interface MilestoneService {
    MilestoneResponse createMilestone(MilestoneCreateDTO milestoneCreateDTO);
    MilestoneResponse updateMilestone(Long id, MilestoneUpdateDTO milestoneUpdateDTO);
    MilestoneResponse getMilestoneById(Long id);
    List<MilestoneResponse> getAllMilestones();
    void deleteMilestone(Long id);
}