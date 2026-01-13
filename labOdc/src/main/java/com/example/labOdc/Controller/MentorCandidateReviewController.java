package com.example.labOdc.Controller;

import com.example.labOdc.DTO.MentorCandidateReviewDTO;
import com.example.labOdc.DTO.Response.MentorCandidateReviewResponse;
import com.example.labOdc.Service.MentorCandidateReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mentor-candidate-reviews")
public class MentorCandidateReviewController {

    @Autowired
    private MentorCandidateReviewService service;

    @PostMapping
    public ResponseEntity<MentorCandidateReviewResponse> create(@RequestBody MentorCandidateReviewDTO dto) {
        MentorCandidateReviewDTO created = service.create(dto);
        MentorCandidateReviewResponse resp = MentorCandidateReviewResponse.builder()
            .id(created.getId())
            .mentorId(created.getMentorId())
            .talentId(created.getTalentId())
            .projectId(created.getProjectId())
            .rating(created.getRating())
            .comments(created.getComments())
            .status(created.getStatus())
            .reviewedById(created.getReviewedById())
            .reviewedAt(created.getReviewedAt())
            .build();
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentorCandidateReviewResponse> getById(@PathVariable String id) {
        MentorCandidateReviewDTO dto = service.getById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        MentorCandidateReviewResponse resp = MentorCandidateReviewResponse.builder()
                .id(dto.getId())
                .mentorId(dto.getMentorId())
                .talentId(dto.getTalentId())
                .projectId(dto.getProjectId())
                .rating(dto.getRating())
                .comments(dto.getComments())
                .status(dto.getStatus())
                .reviewedById(dto.getReviewedById())
                .reviewedAt(dto.getReviewedAt())
                .build();
        return ResponseEntity.ok(resp);
    }

    @GetMapping
    public ResponseEntity<List<MentorCandidateReviewResponse>> list(
            @RequestParam(required = false) String mentorId,
            @RequestParam(required = false) String talentId,
            @RequestParam(required = false) String projectId
    ) {
        List<MentorCandidateReviewDTO> dtos;
        if (mentorId != null) dtos = service.findByMentorId(mentorId);
        else if (talentId != null) dtos = service.findByTalentId(talentId);
        else if (projectId != null) dtos = service.findByProjectId(projectId);
        else dtos = List.of();
        List<MentorCandidateReviewResponse> res = dtos.stream().map(d -> MentorCandidateReviewResponse.builder()
                .id(d.getId())
                .mentorId(d.getMentorId())
                .talentId(d.getTalentId())
                .projectId(d.getProjectId())
                .rating(d.getRating())
                .comments(d.getComments())
                .status(d.getStatus())
                .reviewedById(d.getReviewedById())
                .reviewedAt(d.getReviewedAt())
                .build()).collect(Collectors.toList());
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MentorCandidateReviewResponse> update(@PathVariable String id, @RequestBody MentorCandidateReviewDTO dto) {
        MentorCandidateReviewDTO updated = service.update(id, dto);
        if (updated == null) return ResponseEntity.notFound().build();
        MentorCandidateReviewResponse resp = MentorCandidateReviewResponse.builder()
                .id(updated.getId())
                .mentorId(updated.getMentorId())
                .talentId(updated.getTalentId())
                .projectId(updated.getProjectId())
                .rating(updated.getRating())
                .comments(updated.getComments())
                .status(updated.getStatus())
                .reviewedById(updated.getReviewedById())
                .reviewedAt(updated.getReviewedAt())
                .build();
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
