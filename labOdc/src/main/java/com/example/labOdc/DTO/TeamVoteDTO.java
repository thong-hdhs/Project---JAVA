package com.example.labOdc.DTO;

import com.example.labOdc.Model.TeamVote;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TeamVoteDTO {

    private String projectId;
    private String talentId;
    private TeamVote.ProposalType proposalType;
    private String proposalId;
    private TeamVote.Vote vote;
    private LocalDateTime votedAt;
}
