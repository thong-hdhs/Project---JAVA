package com.example.labOdc.Repository;

import com.example.labOdc.Model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {

    List<Report> findByProjectId(String projectId);

    List<Report> findByMentorId(String mentorId);

    List<Report> findByStatus(Report.Status status);
}
