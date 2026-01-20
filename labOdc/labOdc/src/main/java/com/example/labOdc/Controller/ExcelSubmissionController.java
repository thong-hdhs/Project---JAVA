package com.example.labOdc.Controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.labOdc.Enum.TemplateType;
import com.example.labOdc.Model.ExcelSubmission;
import com.example.labOdc.Service.ExcelSubmissionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/excel-submissions")
@RequiredArgsConstructor
public class ExcelSubmissionController {
    private final ExcelSubmissionService service;

    @PreAuthorize("hasAnyAuthority('TALENT_UPDATE_TASK','MENTOR_ASSIGN_TASK')")
    @PostMapping
    public ExcelSubmission submitExcel(
            @RequestParam String projectId,
            @RequestParam String templateId,
            @RequestParam TemplateType type,
            @RequestParam String fileUrl,
            @RequestParam String userId) {
        return service.submitExcel(projectId, templateId, type, fileUrl, userId);
    }

    @PreAuthorize("hasAuthority('MENTOR_REVIEW_TASK')")
    @PutMapping("/{id}/review")
    public ExcelSubmission review(
            @PathVariable String id,
            @RequestParam String reviewerId,
            @RequestParam boolean approved,
            @RequestParam(required = false) String comment) {
        return service.reviewSubmission(id, reviewerId, comment, approved);
    }

    @PreAuthorize("hasAnyAuthority('LAB_VIEW_ALL_DATA','MENTOR_REVIEW_TASK')")
    @GetMapping("/project/{projectId}")
    public List<ExcelSubmission> getByProject(@PathVariable String projectId) {
        return service.getByProject(projectId);
    }

    @GetMapping("/my/{userId}")
    public List<ExcelSubmission> mySubmissions(@PathVariable String userId) {
        return service.getMySubmissions(userId);
    }
}
