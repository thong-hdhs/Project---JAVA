package com.example.labOdc.Service.Implement;

import com.example.labOdc.DTO.TaskCommentDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.TaskComment;
import com.example.labOdc.Repository.TaskCommentRepository;
import com.example.labOdc.Service.TaskCommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskCommentServiceImpl implements TaskCommentService {

    private final TaskCommentRepository repository;

    @Override
    public TaskComment create(TaskCommentDTO taskCommentDTO) {
        TaskComment comment = TaskComment.builder()
                .taskId(taskCommentDTO.getTaskId())
                .userId(taskCommentDTO.getUserId())
                .comment(taskCommentDTO.getComment())
                .attachments(taskCommentDTO.getAttachments())
                .build();
        return repository.save(comment);
    }

    @Override
    public List<TaskComment> getAll() {
        return repository.findAll();
    }

    @Override
    public TaskComment getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy comment"));
    }

    @Override
    public List<TaskComment> getByTaskId(String taskId) {
        return repository.findByTaskId(taskId);
    }

    @Override
    public TaskComment update(String id, TaskCommentDTO taskCommentDTO) {
        TaskComment comment = getById(id);

        if (taskCommentDTO.getComment() != null)
            comment.setComment(taskCommentDTO.getComment());

        if (taskCommentDTO.getAttachments() != null)
            comment.setAttachments(taskCommentDTO.getAttachments());

        return repository.save(comment);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
