package com.example.labOdc.Repository;

import com.example.labOdc.Model.TeamVote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamVoteRepository extends JpaRepository<TeamVote, String> {

    boolean existsByProjectIdAndTalentIdAndProposalTypeAndProposalId(
            String projectId,
            String talentId,
            TeamVote.ProposalType proposalType,
            String proposalId
    );

    List<TeamVote> findByProjectId(String projectId);

    List<TeamVote> findByProposalTypeAndProposalId(
            TeamVote.ProposalType proposalType,
            String proposalId
    );
}
