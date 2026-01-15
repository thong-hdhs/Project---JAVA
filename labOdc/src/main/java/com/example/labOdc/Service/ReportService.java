package com.example.labOdc.Service;

import com.example.labOdc.DTO.ReportDTO;
import com.example.labOdc.Model.Report;

import java.util.List;

public interface ReportService {

    Report create(ReportDTO reportDTO);

    List<Report> getAll();

    Report getById(String id);

    List<Report> getByProject(String projectId);

    List<Report> getByMentor(String mentorId);

    Report update(String id, ReportDTO reportDTO);

    void delete(String id);

    Report submit(String id);

    Report review(String id, Report.Status status, String reviewer, String notes);
}
