package com.example.labOdc.Repository;

import com.example.labOdc.Model.TeamVote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamVoteRepository extends JpaRepository<TeamVote, String> {

    List<TeamVote> findByProjectId(String projectId);

    List<TeamVote> findByTalentId(String talentId);

    List<TeamVote> findByProjectIdAndProposalTypeAndProposalId(
            String projectId,
            TeamVote.ProposalType proposalType,
            String proposalId);

    Optional<TeamVote> findByProjectIdAndTalentIdAndProposalTypeAndProposalId(
            String projectId,
            String talentId,
            TeamVote.ProposalType proposalType,
            String proposalId);
}
