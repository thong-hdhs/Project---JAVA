package com.example.labOdc.Service;

import java.util.List;

import com.example.labOdc.DTO.MentorCandidateReviewDTO;

public interface MentorCandidateReviewService {
    MentorCandidateReviewDTO create(MentorCandidateReviewDTO dto);

    MentorCandidateReviewDTO getById(String id);

    List<MentorCandidateReviewDTO> findByMentorId(String mentorId);

    List<MentorCandidateReviewDTO> findByTalentId(String talentId);

    List<MentorCandidateReviewDTO> findByProjectId(String projectId);

    MentorCandidateReviewDTO update(String id, MentorCandidateReviewDTO dto);

    void delete(String id);
}
