package com.example.labOdc.Service.Implement;

import com.example.labOdc.DTO.Response.TeamVoteResponse;
import com.example.labOdc.DTO.TeamVoteDTO;
import com.example.labOdc.Model.TeamVote;
import com.example.labOdc.Repository.TeamVoteRepository;
import com.example.labOdc.Service.TeamVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamVoteServiceImpl implements TeamVoteService {

    private final TeamVoteRepository teamVoteRepository;

    @Override
    public TeamVoteResponse createVote(TeamVoteDTO teamVoteDTO) {

        boolean existed = teamVoteRepository
                .existsByProjectIdAndTalentIdAndProposalTypeAndProposalId(
                        teamVoteDTO.getProjectId(),
                        teamVoteDTO.getTalentId(),
                        teamVoteDTO.getProposalType(),
                        teamVoteDTO.getProposalId()
                );

        if (existed) {
            throw new RuntimeException("Các chuyên gia đã bỏ phiếu cho đề xuất này.");
        }

        TeamVote vote = TeamVote.builder()
                .projectId(teamVoteDTO.getProjectId())
                .talentId(teamVoteDTO.getTalentId())
                .proposalType(teamVoteDTO.getProposalType())
                .proposalId(teamVoteDTO.getProposalId())
                .vote(teamVoteDTO.getVote())
                .votedAt(teamVoteDTO.getVotedAt())
                .build();

        return TeamVoteResponse.fromEntity(teamVoteRepository.save(vote));
    }

    @Override
    public TeamVoteResponse getById(String id) {
        return teamVoteRepository.findById(id)
                .map(TeamVoteResponse::fromEntity)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu bầu"));
    }

    @Override
    public List<TeamVoteResponse> getByProject(String projectId) {
        return teamVoteRepository.findByProjectId(projectId)
                .stream()
                .map(TeamVoteResponse::fromEntity)
                .toList();
    }

    @Override
    public List<TeamVoteResponse> getByProposal(
            TeamVote.ProposalType proposalType,
            String proposalId
    ) {
        return teamVoteRepository
                .findByProposalTypeAndProposalId(proposalType, proposalId)
                .stream()
                .map(TeamVoteResponse::fromEntity)
                .toList();
    }

    @Override
    public void deleteVote(String id) {
        teamVoteRepository.deleteById(id);
    }
}