package com.example.labOdc.Controller;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.Response.TeamVoteResponse;
import com.example.labOdc.DTO.TeamVoteDTO;
import com.example.labOdc.Model.TeamVote;
import com.example.labOdc.Service.TeamVoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teamvotes")
@RequiredArgsConstructor
public class TeamVoteController {

    private final TeamVoteService teamVoteService;


// Talent vote cho proposal
// talentId thường lấy từ JWT

    @PostMapping("/{talentId}")
    @PreAuthorize("hasRole('TALENT')")
    public ApiResponse<TeamVoteResponse> vote(
            @PathVariable String talentId,
            @Valid @RequestBody TeamVoteDTO dto) {

        TeamVote vote = teamVoteService.vote(dto, talentId);

        return ApiResponse.success(
                TeamVoteResponse.fromEntity(vote),
                "Vote thành công",
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TALENT')")
    public ApiResponse<TeamVoteResponse> update(
            @PathVariable String id,
            @Valid @RequestBody TeamVoteDTO dto) {

        TeamVote vote = teamVoteService.updateVote(id, dto);
        return ApiResponse.success(TeamVoteResponse.fromEntity(vote), "Cập nhật phiếu thành công", HttpStatus.OK);
    }
// Lấy tất cả vote

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('MENTOR','TALENT','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<List<TeamVoteResponse>> getAll() {
        return ApiResponse.success(
                teamVoteService.getAll()
                        .stream()
                        .map(TeamVoteResponse::fromEntity)
                        .toList(),
                "Thành công",
                HttpStatus.OK);
    }


// Lấy vote theo ID

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MENTOR','TALENT','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<TeamVoteResponse> getById(@PathVariable String id) {
        return ApiResponse.success(
                TeamVoteResponse.fromEntity(
                        teamVoteService.getById(id)),
                "Thành công",
                HttpStatus.OK);
    }


//     Lấy vote theo project

    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAnyRole('MENTOR','TALENT','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<List<TeamVoteResponse>> getByProject(
            @PathVariable String projectId) {

        return ApiResponse.success(
                teamVoteService.getByProject(projectId)
                        .stream()
                        .map(TeamVoteResponse::fromEntity)
                        .toList(),
                "Thành công",
                HttpStatus.OK);
    }


//  Lấy vote theo talent

    @GetMapping("/talent/{talentId}")
    @PreAuthorize("hasAnyRole('MENTOR','TALENT','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<List<TeamVoteResponse>> getByTalent(
            @PathVariable String talentId) {

        return ApiResponse.success(
                teamVoteService.getByTalent(talentId)
                        .stream()
                        .map(TeamVoteResponse::fromEntity)
                        .toList(),
                "Thành công",
                HttpStatus.OK);
    }


// Lấy vote theo proposal

    @GetMapping("/proposal")
    public ApiResponse<List<TeamVoteResponse>> getByProposal(
            @RequestParam String projectId,
            @RequestParam TeamVote.ProposalType proposalType,
            @RequestParam String proposalId) {

        return ApiResponse.success(
                teamVoteService.getByProposal(projectId, proposalType, proposalId)
                        .stream()
                        .map(TeamVoteResponse::fromEntity)
                        .toList(),
                "Thành công",
                HttpStatus.OK);
    }


// Xóa vote
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TALENT','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<String> delete(@PathVariable String id) {
        teamVoteService.deleteVote(id);
        return ApiResponse.success("Xóa thành công", "OK", HttpStatus.OK);
    }
}