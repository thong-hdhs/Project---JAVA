package com.example.labOdc.Repository;

import com.example.labOdc.Model.MemberContribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberContributionRepository extends JpaRepository<MemberContribution, String> {

    List<MemberContribution> findByProjectId(String projectId);

    List<MemberContribution> findByTalentId(String talentId);

    List<MemberContribution> findByRecordedById(String recordedById);

    List<MemberContribution> findByContributionType(MemberContribution.ContributionType type);
}

