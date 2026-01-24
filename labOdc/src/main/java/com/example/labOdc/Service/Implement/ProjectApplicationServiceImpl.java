package com.example.labOdc.Service.Implement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.ProjectApplicationDTO;
import com.example.labOdc.DTO.Response.ProjectApplicationResponse;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Project;
import com.example.labOdc.Model.ProjectApplication;
import com.example.labOdc.Repository.ProjectApplicationRepository;
import com.example.labOdc.Repository.ProjectRepository;
import com.example.labOdc.Repository.TalentRepository;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.ProjectApplicationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectApplicationServiceImpl implements ProjectApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectApplicationServiceImpl.class);

    private final ProjectApplicationRepository applicationRepository;
    private final ProjectRepository projectRepository;
    private final TalentRepository talentRepository;
    private final UserRepository userRepository;

    /**
     * Chức năng: Tạo đơn ứng tuyển dự án mới.
     * Repository: ProjectApplicationRepository.save() - Lưu entity vào database.
     */
    @Override
    public ProjectApplicationResponse createApplication(ProjectApplicationDTO dto) {
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        ProjectApplication pa = ProjectApplication.builder()
                .project(project)
                .coverLetter(dto.getCoverLetter())
                .status(ProjectApplication.Status.PENDING)
                .build();

        ProjectApplication saved = applicationRepository.save(pa);
        return ProjectApplicationResponse.from(saved);
    }

    /**
     * Chức năng: Lấy danh sách tất cả đơn ứng tuyển.
     * Repository: ProjectApplicationRepository.findAll() - Truy vấn tất cả entities.
     */
    @Override
    public List<ProjectApplicationResponse> getAllApplications() {
        return applicationRepository.findAll().stream()
                .map(ProjectApplicationResponse::from)
                .toList();
    }

    /**
     * Chức năng: Lấy đơn ứng tuyển theo ID.
     * Repository: ProjectApplicationRepository.findById() - Truy vấn entity theo ID.
     */
    @Override
    public ProjectApplicationResponse getApplicationById(String id) {
        ProjectApplication pa = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        return ProjectApplicationResponse.from(pa);
    }

    /**
     * Chức năng: Xóa đơn ứng tuyển theo ID.
     * Repository: ProjectApplicationRepository.deleteById() - Xóa entity theo ID.
     */
    @Override
    public void deleteApplication(String id) {
        applicationRepository.deleteById(id);
    }

    /**
     * Chức năng: Cập nhật đơn ứng tuyển theo ID.
     * Repository: ProjectApplicationRepository.findById() và save() - Tìm và cập nhật entity.
     */
    @Override
    public ProjectApplicationResponse updateApplication(ProjectApplicationDTO dto, String id) {
        ProjectApplication pa = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        if (dto.getCoverLetter() != null) pa.setCoverLetter(dto.getCoverLetter());

        ProjectApplication updated = applicationRepository.save(pa);
        return ProjectApplicationResponse.from(updated);
    }

    /**
     * Chức năng: Lọc danh sách đơn ứng tuyển theo Project ID.
     * Repository: ProjectApplicationRepository.findByProjectId() - Truy vấn theo projectId.
     */
    @Override
    public List<ProjectApplicationResponse> findByProjectId(String projectId) {
        return applicationRepository.findByProjectId(projectId).stream()
                .map(ProjectApplicationResponse::from)
                .toList();
    }

    /**
     * Chức năng: Lọc danh sách đơn ứng tuyển theo Talent ID.
     * Repository: ProjectApplicationRepository.findByTalentId() - Truy vấn theo talentId.
     */
    @Override
    public List<ProjectApplicationResponse> findByTalentId(String talentId) {
        return applicationRepository.findByTalentId(talentId).stream()
                .map(ProjectApplicationResponse::from)
                .toList();
    }

    /**
     * Chức năng: Phê duyệt đơn ứng tuyển dự án.
     * Repository: ProjectApplicationRepository.findById(), UserRepository.findById(), save() - Cập nhật trạng thái và thông tin phê duyệt.
     */
    @Override
    public ProjectApplicationResponse approveApplication(String id, String reviewerId) {
        logger.info("Phê duyệt đơn ứng tuyển ID: {}", id);
        ProjectApplication pa = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        // Placeholder: Tìm reviewer từ UserRepository
        // User reviewer = userRepository.findById(reviewerId).orElseThrow(() -> new ResourceNotFoundException("Reviewer not found"));
        pa.setStatus(ProjectApplication.Status.APPROVED);
        // pa.setReviewedBy(reviewer);
        pa.setReviewedAt(LocalDateTime.now());
        ProjectApplication saved = applicationRepository.save(pa);
        return ProjectApplicationResponse.from(saved);
    }

    /**
     * Chức năng: Từ chối đơn ứng tuyển dự án.
     * Repository: ProjectApplicationRepository.findById(), UserRepository.findById(), save() - Cập nhật trạng thái và lý do từ chối.
     */
    @Override
    public ProjectApplicationResponse rejectApplication(String id, String reviewerId, String reason) {
        logger.info("Từ chối đơn ứng tuyển ID: {} với lý do: {}", id, reason);
        ProjectApplication pa = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        // User reviewer = userRepository.findById(reviewerId).orElseThrow(() -> new ResourceNotFoundException("Reviewer not found"));
        pa.setStatus(ProjectApplication.Status.REJECTED);
        // pa.setReviewedBy(reviewer);
        pa.setReviewedAt(LocalDateTime.now());
        pa.setRejectionReason(reason);
        ProjectApplication saved = applicationRepository.save(pa);
        return ProjectApplicationResponse.from(saved);
    }

    
    /**
     * Chức năng: Hủy đơn ứng tuyển dự án.
     * Repository: ProjectApplicationRepository.findById(), save() - Cập nhật trạng thái thành WITHDRAWN.
     */
    @Override
    public ProjectApplicationResponse cancelApplication(String id) {
        logger.info("Hủy đơn ứng tuyển ID: {}", id);
        ProjectApplication pa = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        pa.setStatus(ProjectApplication.Status.WITHDRAWN);
        ProjectApplication saved = applicationRepository.save(pa);
        return ProjectApplicationResponse.from(saved);
    }

    

}
