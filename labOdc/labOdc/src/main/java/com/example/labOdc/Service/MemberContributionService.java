package com.example.labOdc.Service;

import com.example.labOdc.DTO.MemberContributionDTO;
import com.example.labOdc.Model.MemberContribution;

import java.util.List;

public interface MemberContributionService {

    MemberContribution createContribution(
            MemberContributionDTO dto,
            String recordedByUserId);

    MemberContribution getById(String id);

    List<MemberContribution> getAll();

    List<MemberContribution> getByProject(String projectId);

    List<MemberContribution> getByTalent(String talentId);

    List<MemberContribution> getByType(MemberContribution.ContributionType type);

    List<MemberContribution> getByRecorder(String userId);

    MemberContribution updateContribution(String id, MemberContributionDTO dto);

    void deleteContribution(String id);
}
