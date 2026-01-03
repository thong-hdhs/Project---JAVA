package com.example.labOdc.DTO;

import com.example.labOdc.Model.Task;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TaskDTO {
    private String projectId;
    private String assignedTo;
    private String createdBy;
    private String taskName;
    private String description;
    private Task.Priority priority;
    private Task.Status status;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate completedDate;
    private BigDecimal estimatedHours;
    private BigDecimal actualHours;
    private String excelTemplateUrl;
    private List<String>  attachments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

