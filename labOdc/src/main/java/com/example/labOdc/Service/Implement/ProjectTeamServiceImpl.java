package com.example.labOdc.Service.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.ProjectTeamDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.ProjectTeam;
import com.example.labOdc.Repository.ProjectTeamRepository;
import com.example.labOdc.Service.ProjectTeamService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectTeamServiceImpl implements ProjectTeamService {

    private final ProjectTeamRepository projectTeamRepository;

    @Override
    public ProjectTeam createProjectTeam(ProjectTeamDTO dto) {
        if (projectTeamRepository.existsByProjectIdAndTalentId(dto.getProjectId(), dto.getTalentId())) {
            throw new IllegalArgumentException("Talent already in this project team");
        }

        ProjectTeam pt = ProjectTeam.builder()
                .projectId(dto.getProjectId())
                .talentId(dto.getTalentId())
                .isLeader(dto.getIsLeader() != null ? dto.getIsLeader() : false)
                .joinedDate(dto.getJoinedDate())
                .leftDate(dto.getLeftDate())
                .status(dto.getStatus())
                .performanceRating(dto.getPerformanceRating())
                .build();

        projectTeamRepository.save(pt);
        return pt;
    }

    @Override
    public List<ProjectTeam> getAllProjectTeam() {
        return projectTeamRepository.findAll();
    }

    @Override
    public ProjectTeam getProjectTeamById(String id) {
        return projectTeamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProjectTeam", "id", id));
    }

    @Override
    public ProjectTeam updateProjectTeam(ProjectTeamDTO dto, String id) {
        ProjectTeam pt = projectTeamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProjectTeam", "id", id));

        // Lưu ý: update projectId/talentId có thể gây trùng unique -> nếu bạn muốn an
        // toàn, mình sẽ thêm check ở đây.
        if (dto.getProjectId() != null)
            pt.setProjectId(dto.getProjectId());
        if (dto.getTalentId() != null)
            pt.setTalentId(dto.getTalentId());

        if (dto.getIsLeader() != null)
            pt.setIsLeader(dto.getIsLeader());
        if (dto.getJoinedDate() != null)
            pt.setJoinedDate(dto.getJoinedDate());
        if (dto.getLeftDate() != null)
            pt.setLeftDate(dto.getLeftDate());
        if (dto.getStatus() != null)
            pt.setStatus(dto.getStatus());
        if (dto.getPerformanceRating() != null)
            pt.setPerformanceRating(dto.getPerformanceRating());

        projectTeamRepository.save(pt);
        return pt;
    }

    @Override
    public void deleteProjectTeam(String id) {
        projectTeamRepository.deleteById(id);
    }
}