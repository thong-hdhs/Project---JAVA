package com.example.labOdc.Service;

import com.example.labOdc.DTO.TaskCommentDTO;
import com.example.labOdc.Model.TaskComment;

import java.util.List;

public interface TaskCommentService {

    TaskComment create(TaskCommentDTO taskCommentDTO);

    List<TaskComment> getAll();

    TaskComment getById(String id);

    List<TaskComment> getByTaskId(String taskId);

    TaskComment update(String id, TaskCommentDTO taskCommentDTO);

    void delete(String id);
}

