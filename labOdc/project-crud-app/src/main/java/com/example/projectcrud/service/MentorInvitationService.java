package com.example.projectcrud.service;

import com.example.projectcrud.dto.mentorinvitation.MentorInvitationCreateDTO;
import com.example.projectcrud.dto.mentorinvitation.MentorInvitationUpdateDTO;
import com.example.projectcrud.model.MentorInvitation;
import com.example.projectcrud.response.mentorinvitation.MentorInvitationResponse;

import java.util.List;

public interface MentorInvitationService {
    MentorInvitationResponse createMentorInvitation(MentorInvitationCreateDTO createDTO);
    MentorInvitationResponse updateMentorInvitation(Long id, MentorInvitationUpdateDTO updateDTO);
    MentorInvitationResponse getMentorInvitationById(Long id);
    List<MentorInvitationResponse> getAllMentorInvitations();
    void deleteMentorInvitation(Long id);
}