package com.example.labOdc.Service;


import com.example.labOdc.DTO.Response.TeamVoteResponse;
import com.example.labOdc.DTO.TeamVoteDTO;
import com.example.labOdc.Model.TeamVote;

import java.util.List;

public interface TeamVoteService {

    TeamVote vote(
            TeamVoteDTO dto,
            String talentId);

    TeamVote getById(String id);

    TeamVote updateVote(String id, TeamVoteDTO dto);

    List<TeamVote> getAll();

    List<TeamVote> getByProject(String projectId);

    List<TeamVote> getByTalent(String talentId);

    List<TeamVote> getByProposal(
            String projectId,
            TeamVote.ProposalType proposalType,
            String proposalId);

    void deleteVote(String id);
}