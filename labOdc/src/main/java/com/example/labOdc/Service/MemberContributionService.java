package com.example.labOdc.Service;

import com.example.labOdc.DTO.MemberContributionDTO;
import com.example.labOdc.Model.MemberContribution;

import java.util.List;

public interface MemberContributionService {

    MemberContribution create(MemberContributionDTO memberContributionDTO);

    MemberContribution getById(String id);

    List<MemberContribution> getAll();

    List<MemberContribution> getByProject(String projectId);

    List<MemberContribution> getByTalent(String talentId);

    MemberContribution update(String id, MemberContributionDTO memberContributionDTO);

    void delete(String id);
}
