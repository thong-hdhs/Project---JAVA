package com.example.labOdc.Service;

import java.util.List;

import com.example.labOdc.DTO.MentorInvitationDTO;
import com.example.labOdc.Model.MentorInvitation;

public interface MentorInvitationService {
    MentorInvitation createMentorInvitation(MentorInvitationDTO dto);

    List<MentorInvitation> getAllMentorInvitation();

    MentorInvitation getMentorInvitationById(String id);

    MentorInvitation updateMentorInvitation(MentorInvitationDTO dto, String id);

    void deleteMentorInvitation(String id);
}