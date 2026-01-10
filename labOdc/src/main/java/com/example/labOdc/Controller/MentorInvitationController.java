package com.example.labOdc.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.MentorInvitationDTO;
import com.example.labOdc.DTO.Response.MentorInvitationResponse;
import com.example.labOdc.Model.MentorInvitation;
import com.example.labOdc.Service.MentorInvitationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/mentor-invitations")
public class MentorInvitationController {

    private final MentorInvitationService mentorInvitationService;

    @PostMapping("/")
    public ApiResponse<MentorInvitationResponse> create(@Valid @RequestBody MentorInvitationDTO dto,
            BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ApiResponse.error(errorMessages);
        }

        MentorInvitation mi = mentorInvitationService.createMentorInvitation(dto);
        return ApiResponse.success(MentorInvitationResponse.fromMentorInvitation(mi), "Thanh cong", HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ApiResponse<List<MentorInvitationResponse>> getAll() {
        List<MentorInvitation> list = mentorInvitationService.getAllMentorInvitation();
        return ApiResponse.success(list.stream().map(MentorInvitationResponse::fromMentorInvitation).toList(),
                "Thanh cong", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ApiResponse<MentorInvitationResponse> getById(@PathVariable String id) {
        MentorInvitation mi = mentorInvitationService.getMentorInvitationById(id);
        return ApiResponse.success(MentorInvitationResponse.fromMentorInvitation(mi), "Thanh cong", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ApiResponse<MentorInvitationResponse> update(@Valid @RequestBody MentorInvitationDTO dto,
            @PathVariable String id) {
        MentorInvitation mi = mentorInvitationService.updateMentorInvitation(dto, id);
        return ApiResponse.success(MentorInvitationResponse.fromMentorInvitation(mi), "Thanh cong", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        mentorInvitationService.deleteMentorInvitation(id);
        return ApiResponse.success("Xoa thanh cong", "Thanh cong", HttpStatus.OK);
    }
}