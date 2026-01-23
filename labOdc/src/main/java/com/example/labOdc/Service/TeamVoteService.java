package com.example.labOdc.Service;


import com.example.labOdc.DTO.Response.TeamVoteResponse;
import com.example.labOdc.DTO.TeamVoteDTO;
import com.example.labOdc.Model.TeamVote;

import java.util.List;

public interface TeamVoteService {

    TeamVoteResponse createVote(TeamVoteDTO teamVoteDTO);

    TeamVoteResponse getById(String id);

    List<TeamVoteResponse> getByProject(String projectId);

    List<TeamVoteResponse> getByProposal(
            TeamVote.ProposalType proposalType,
            String proposalId
    );

    void deleteVote(String id);
}