package com.example.labOdc.Service;

import com.example.labOdc.DTO.TaskCommentDTO;
import com.example.labOdc.Model.TaskComment;

import java.util.List;

public interface TaskCommentService {

    TaskComment createComment(TaskCommentDTO dto, String userId);

    List<TaskComment> getCommentsByTask(String taskId);

    List<TaskComment> getByUser(String userId);

    TaskComment getById(String id);

    TaskComment updateComment(String id, TaskCommentDTO dto);

    void deleteComment(String id);
}

