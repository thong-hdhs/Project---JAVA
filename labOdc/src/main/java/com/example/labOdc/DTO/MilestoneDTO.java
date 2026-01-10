package com.example.labOdc.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.labOdc.Model.MilestoneStatus;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MilestoneDTO {
    private String projectId;
    private String milestoneName;
    private String description;
    private LocalDate dueDate;
    private LocalDate completedDate;
    private MilestoneStatus status;
    private BigDecimal paymentPercentage;
    private String deliverables;
}