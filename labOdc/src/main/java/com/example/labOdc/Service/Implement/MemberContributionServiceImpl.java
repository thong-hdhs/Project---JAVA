package com.example.labOdc.Service.Implement;


import com.example.labOdc.DTO.MemberContributionDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.MemberContribution;
import com.example.labOdc.Repository.MemberContributionRepository;
import com.example.labOdc.Service.MemberContributionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MemberContributionServiceImpl
        implements MemberContributionService {

    private final MemberContributionRepository repository;

    @Override
    public MemberContribution create(MemberContributionDTO memberContributionDTO) {

        MemberContribution mc = MemberContribution.builder()
                .projectId(memberContributionDTO.getProjectId())
                .talentId(memberContributionDTO.getTalentId())
                .contributionType(memberContributionDTO.getContributionType())
                .description(memberContributionDTO.getDescription())
                .score(memberContributionDTO.getScore())
                .recordedBy(memberContributionDTO.getRecordedBy())
                .recordedAt(LocalDateTime.now())
                .build();

        return repository.save(mc);
    }

    @Override
    public MemberContribution getById(String id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Không tìm thấy contribution"));
    }

    @Override
    public List<MemberContribution> getAll() {
        return repository.findAll();
    }

    @Override
    public List<MemberContribution> getByProject(String projectId) {
        return repository.findByProjectId(projectId);
    }

    @Override
    public List<MemberContribution> getByTalent(String talentId) {
        return repository.findByTalentId(talentId);
    }

    @Override
    public MemberContribution update(String id, MemberContributionDTO memberContributionDTO) {

        MemberContribution mc = getById(id);

        if (memberContributionDTO.getContributionType() != null)
            mc.setContributionType(memberContributionDTO.getContributionType());

        if (memberContributionDTO.getDescription() != null)
            mc.setDescription(memberContributionDTO.getDescription());

        if (memberContributionDTO.getScore() != null)
            mc.setScore(memberContributionDTO.getScore());

        return repository.save(mc);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
