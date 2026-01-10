package com.example.projectcrud.serviceimpl;

import com.example.projectcrud.dto.mentorinvitation.MentorInvitationCreateDTO;
import com.example.projectcrud.dto.mentorinvitation.MentorInvitationUpdateDTO;
import com.example.projectcrud.exception.ResourceNotFoundException;
import com.example.projectcrud.model.MentorInvitation;
import com.example.projectcrud.repository.MentorInvitationRepository;
import com.example.projectcrud.response.mentorinvitation.MentorInvitationResponse;
import com.example.projectcrud.service.MentorInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MentorInvitationServiceImpl implements MentorInvitationService {

    @Autowired
    private MentorInvitationRepository mentorInvitationRepository;

    @Override
    public MentorInvitationResponse createMentorInvitation(MentorInvitationCreateDTO createDTO) {
        MentorInvitation mentorInvitation = new MentorInvitation();
        // Set properties from createDTO to mentorInvitation
        mentorInvitation = mentorInvitationRepository.save(mentorInvitation);
        return new MentorInvitationResponse(mentorInvitation);
    }

    @Override
    public MentorInvitationResponse updateMentorInvitation(Long id, MentorInvitationUpdateDTO updateDTO) {
        MentorInvitation mentorInvitation = mentorInvitationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor Invitation not found"));
        // Update properties from updateDTO to mentorInvitation
        mentorInvitation = mentorInvitationRepository.save(mentorInvitation);
        return new MentorInvitationResponse(mentorInvitation);
    }

    @Override
    public void deleteMentorInvitation(Long id) {
        mentorInvitationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor Invitation not found"));
        mentorInvitationRepository.deleteById(id);
    }

    @Override
    public List<MentorInvitationResponse> getAllMentorInvitations() {
        return mentorInvitationRepository.findAll().stream()
                .map(MentorInvitationResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public MentorInvitationResponse getMentorInvitationById(Long id) {
        MentorInvitation mentorInvitation = mentorInvitationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor Invitation not found"));
        return new MentorInvitationResponse(mentorInvitation);
    }
}