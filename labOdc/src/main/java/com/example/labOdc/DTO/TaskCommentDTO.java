package com.example.labOdc.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskCommentDTO {

    private String taskId;
    private String userId;
    private String comment;
    private String attachments;
}
