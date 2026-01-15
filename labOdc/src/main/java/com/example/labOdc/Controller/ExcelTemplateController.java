package com.example.labOdc.Controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.labOdc.Enum.TemplateType;
import com.example.labOdc.Model.ExcelTemplate;
import com.example.labOdc.Service.ExcelTemplateService;

public class ExcelTemplateController {
    private final ExcelTemplateService service;

    public ExcelTemplateController(ExcelTemplateService excelTemplateService) {
        this.service = excelTemplateService;
    }

    @PreAuthorize("hasAuthority('SYSTEM_MANAGE_TEMPLATES')")
    @PostMapping
    public ExcelTemplate createTemplate(
            @RequestBody ExcelTemplate template,
            @RequestParam String adminId) {
        return service.createTemplate(template, adminId);
    }

    // ALL ROLES
    @GetMapping
    public List<ExcelTemplate> getActiveTemplates() {
        return service.getActiveTemplates();
    }

    @GetMapping("/type/{type}")
    public List<ExcelTemplate> getByType(@PathVariable TemplateType type) {
        return service.getTemplatesByType(type);
    }

    // SYSTEM ADMIN
    @PutMapping("/{id}/disable")
    public ExcelTemplate disable(@PathVariable String id) {
        return service.disableTemplate(id);
    }
}
