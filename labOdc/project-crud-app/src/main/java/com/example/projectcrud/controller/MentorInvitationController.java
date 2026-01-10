package com.example.projectcrud.controller;

import com.example.projectcrud.dto.mentorinvitation.MentorInvitationCreateDTO;
import com.example.projectcrud.dto.mentorinvitation.MentorInvitationUpdateDTO;
import com.example.projectcrud.response.mentorinvitation.MentorInvitationResponse;
import com.example.projectcrud.service.MentorInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mentor-invitations")
public class MentorInvitationController {

    @Autowired
    private MentorInvitationService mentorInvitationService;

    @PostMapping
    public ResponseEntity<MentorInvitationResponse> createMentorInvitation(@RequestBody MentorInvitationCreateDTO createDTO) {
        MentorInvitationResponse response = mentorInvitationService.createMentorInvitation(createDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentorInvitationResponse> getMentorInvitation(@PathVariable Long id) {
        MentorInvitationResponse response = mentorInvitationService.getMentorInvitationById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MentorInvitationResponse>> getAllMentorInvitations() {
        List<MentorInvitationResponse> responses = mentorInvitationService.getAllMentorInvitations();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MentorInvitationResponse> updateMentorInvitation(@PathVariable Long id, @RequestBody MentorInvitationUpdateDTO updateDTO) {
        MentorInvitationResponse response = mentorInvitationService.updateMentorInvitation(id, updateDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentorInvitation(@PathVariable Long id) {
        mentorInvitationService.deleteMentorInvitation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}