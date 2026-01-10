package com.example.projectcrud.controller;

import com.example.projectcrud.dto.milestone.MilestoneCreateDTO;
import com.example.projectcrud.dto.milestone.MilestoneUpdateDTO;
import com.example.projectcrud.response.milestone.MilestoneResponse;
import com.example.projectcrud.service.MilestoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/milestones")
public class MilestoneController {

    private final MilestoneService milestoneService;

    public MilestoneController(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    @PostMapping
    public ResponseEntity<MilestoneResponse> createMilestone(@RequestBody MilestoneCreateDTO milestoneCreateDTO) {
        MilestoneResponse response = milestoneService.createMilestone(milestoneCreateDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MilestoneResponse> getMilestoneById(@PathVariable Long id) {
        MilestoneResponse response = milestoneService.getMilestoneById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MilestoneResponse>> getAllMilestones() {
        List<MilestoneResponse> responses = milestoneService.getAllMilestones();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MilestoneResponse> updateMilestone(@PathVariable Long id, @RequestBody MilestoneUpdateDTO milestoneUpdateDTO) {
        MilestoneResponse response = milestoneService.updateMilestone(id, milestoneUpdateDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable Long id) {
        milestoneService.deleteMilestone(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}