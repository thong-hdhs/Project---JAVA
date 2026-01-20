//package com.example.labOdc.Service.Implement;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.example.labOdc.DTO.ProjectMentorDTO;
//import com.example.labOdc.Exception.ResourceNotFoundException;
//import com.example.labOdc.Model.ProjectMentor;
//import com.example.labOdc.Repository.ProjectMentorRepository;
//import com.example.labOdc.Service.ProjectMentorService;
//
//import lombok.AllArgsConstructor;
//
//@Service
//@AllArgsConstructor
//public class ProjectMentorServiceImpl implements ProjectMentorService {
//
//    private final ProjectMentorRepository projectMentorRepository;
//
//    @Override
//    public ProjectMentor createProjectMentor(ProjectMentorDTO dto) {
//        if (projectMentorRepository.existsByProjectIdAndMentorId(dto.getProjectId(), dto.getMentorId())) {
//            throw new IllegalArgumentException("Mentor already assigned to this project");
//        }
//
//        ProjectMentor pm = ProjectMentor.builder()
//                .projectId(dto.getProjectId())
//                .mentorId(dto.getMentorId())
//                .role(dto.getRole()) // nếu null -> @Builder.Default sẽ dùng MAIN_MENTOR
//                .status(dto.getStatus()) // nếu null -> @Builder.Default sẽ dùng ACTIVE
//                .build();
//
//        projectMentorRepository.save(pm);
//        return pm;
//    }
//
//    @Override
//    public List<ProjectMentor> getAllProjectMentor() {
//        return projectMentorRepository.findAll();
//    }
//
//    @Override
//    public ProjectMentor getProjectMentorById(String id) {
//        return projectMentorRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("ProjectMentor", "id", id));
//    }
//
//    @Override
//    public ProjectMentor updateProjectMentor(ProjectMentorDTO dto, String id) {
//        ProjectMentor pm = projectMentorRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("ProjectMentor", "id", id));
//
//        // Lưu ý: nếu đổi projectId+mentorId có thể đụng unique. Nếu bạn muốn chặt chẽ
//        // hơn mình sẽ thêm check.
//        if (dto.getProjectId() != null)
//            pm.setProjectId(dto.getProjectId());
//        if (dto.getMentorId() != null)
//            pm.setMentorId(dto.getMentorId());
//
//        if (dto.getRole() != null)
//            pm.setRole(dto.getRole());
//        if (dto.getStatus() != null)
//            pm.setStatus(dto.getStatus());
//
//        projectMentorRepository.save(pm);
//        return pm;
//    }
//
//    @Override
//    public void deleteProjectMentor(String id) {
//        projectMentorRepository.deleteById(id);
//    }
//}