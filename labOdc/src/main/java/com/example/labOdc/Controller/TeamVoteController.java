package com.example.labOdc.Controller;

import com.example.labOdc.DTO.Response.TeamVoteResponse;
import com.example.labOdc.DTO.TeamVoteDTO;
import com.example.labOdc.Model.TeamVote;
import com.example.labOdc.Service.TeamVoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teamvotes")
@RequiredArgsConstructor
public class TeamVoteController {

    private final TeamVoteService teamVoteService;

    @PostMapping
    public TeamVoteResponse create(@RequestBody @Valid TeamVoteDTO teamVoteDTO) {
        return teamVoteService.createVote(teamVoteDTO);
    }

    @GetMapping("/{id}")
    public TeamVoteResponse getById(@PathVariable String id) {
        return teamVoteService.getById(id);
    }

    @GetMapping("/project/{projectId}")
    public List<TeamVoteResponse> getByProject(@PathVariable String projectId) {
        return teamVoteService.getByProject(projectId);
    }

    @GetMapping("/proposal")
    public List<TeamVoteResponse> getByProposal(
            @RequestParam TeamVote.ProposalType proposalType,
            @RequestParam String proposalId
    ) {
        return teamVoteService.getByProposal(proposalType, proposalId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        teamVoteService.deleteVote(id);
    }
}