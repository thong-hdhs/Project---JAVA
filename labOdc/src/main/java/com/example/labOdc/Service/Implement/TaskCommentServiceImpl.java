package com.example.labOdc.Service.Implement;

import com.example.labOdc.DTO.TaskCommentDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Task;
import com.example.labOdc.Model.TaskComment;
import com.example.labOdc.Model.User;
import com.example.labOdc.Repository.TaskCommentRepository;
import com.example.labOdc.Repository.TaskRepository;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.TaskCommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskCommentServiceImpl implements TaskCommentService {

    private final TaskCommentRepository taskCommentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public TaskComment createComment(TaskCommentDTO dto, String userId) {

        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Task"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy User"));

        TaskComment comment = TaskComment.builder()
                .task(task)
                .user(user)
                .comment(dto.getComment())
                .attachments(dto.getAttachments())
                .build();

        return taskCommentRepository.save(comment);
    }

    @Override
    public List<TaskComment> getCommentsByTask(String taskId) {
        return taskCommentRepository.findByTaskId(taskId);
    }

    @Override
    public TaskComment getById(String id) {
        return taskCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy comment"));
    }
    @Override
    public List<TaskComment> getByUser(String userId) {
        return taskCommentRepository.findByUserId(userId);
    }
    @Override
    public TaskComment updateComment(String id, TaskCommentDTO dto) {
        TaskComment comment = getById(id);

        if (dto.getComment() != null) {
            comment.setComment(dto.getComment());
        }

        if (dto.getAttachments() != null) {
            comment.setAttachments(dto.getAttachments());
        }

        return taskCommentRepository.save(comment);
    }

    @Override
    public void deleteComment(String id) {
        taskCommentRepository.deleteById(id);
    }
}