package com.example.labOdc.Service.Implement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.ProjectApplicationDTO;
import com.example.labOdc.DTO.Response.ProjectApplicationResponse;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Project;
import com.example.labOdc.Model.ProjectApplication;
import com.example.labOdc.Model.ProjectTeam;
import com.example.labOdc.Model.ProjectTeamStatus;
import com.example.labOdc.Model.Talent;
import com.example.labOdc.Model.User;
import com.example.labOdc.Repository.ProjectApplicationRepository;
import com.example.labOdc.Repository.ProjectRepository;
import com.example.labOdc.Repository.ProjectTeamRepository;
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
    private final ProjectTeamRepository projectTeamRepository;

    /**
     * Chức năng: Tạo đơn ứng tuyển dự án mới.
     * Repository: ProjectApplicationRepository.save() - Lưu entity vào database.
     */
    @Override
    public ProjectApplicationResponse createApplication(ProjectApplicationDTO dto, String requesterUsername) {
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        String principalName = requesterUsername;
        if (principalName == null || principalName.isBlank()) {
            try {
                principalName = SecurityContextHolder.getContext().getAuthentication() != null
                        ? SecurityContextHolder.getContext().getAuthentication().getName()
                        : null;
            } catch (Exception ignored) {
                principalName = null;
            }
        }

        if (principalName == null || principalName.isBlank()) {
            throw new ResourceNotFoundException("Unauthenticated user");
        }

        final String login = principalName;
        User user = userRepository.findByUsername(login)
            .or(() -> userRepository.findByEmail(login))
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Talent talent = talentRepository.findByUserId(user.getId())
                .orElseGet(() -> talentRepository.save(Talent.builder().user(user).build()));

        ProjectApplication pa = ProjectApplication.builder()
                .project(project)
                .talent(talent)
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

        @Override
        public List<ProjectApplicationResponse> getMyApplications(String requesterUsername) {
        if (requesterUsername == null || requesterUsername.isBlank()) {
            return List.of();
        }

        final String login = requesterUsername;
        User user = userRepository.findByUsername(login)
            .or(() -> userRepository.findByEmail(login))
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return talentRepository.findByUserId(user.getId())
            .map(talent -> applicationRepository.findByTalentId(talent.getId()).stream()
                .map(ProjectApplicationResponse::from)
                .toList())
            .orElseGet(List::of);
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

    

    /**
     * Chức năng: Lấy danh sách đơn ứng tuyển đang chờ xử lý theo dự án.
     * Repository: ProjectApplicationRepository.findAll() - Lọc theo projectId và status PENDING.
     */
    @Override
    public List<ProjectApplicationResponse> getPendingApplications(String projectId) {
        return applicationRepository.findAll().stream()
                .filter(pa -> pa.getProject().getId().equals(projectId) && pa.getStatus() == ProjectApplication.Status.PENDING)
                .map(ProjectApplicationResponse::from)
                .toList();
    }

    /**
     * Chức năng: Thêm talent vào team dự án sau khi phê duyệt.
     * Repository: ProjectTeamRepository.save() - Tạo ProjectTeam entity.
     */
    @Override
    public void addTalentToProjectTeam(String applicationId) {
        ProjectApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        if (application.getStatus() != ProjectApplication.Status.APPROVED) {
            throw new IllegalStateException("Application must be approved first");
        }
        ProjectTeam team = ProjectTeam.builder()
                .project(application.getProject())
                .talent(application.getTalent())
                .joinedDate(java.time.LocalDate.now())
                .status(ProjectTeamStatus.ACTIVE)
                .build();
        projectTeamRepository.save(team);
    }

    /**
     * Chức năng: Xóa talent khỏi dự án.
     * Repository: ProjectTeamRepository.findAll(), delete() - Tìm và xóa ProjectTeam entity.
     */
    @Override
    public void removeTalentFromProject(String projectId, String talentId) {
        ProjectTeam team = projectTeamRepository.findAll().stream()
                .filter(pt -> pt.getProject().getId().equals(projectId) && pt.getTalent().getId().equals(talentId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Talent not in project team"));
        team.setStatus(ProjectTeamStatus.REMOVED);
        team.setLeftDate(java.time.LocalDate.now());
        projectTeamRepository.save(team);
    }

    @Override
    public void createApplication(String projectId, String talentId, String coverLetter) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        Talent talent = talentRepository.findById(talentId)
            .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));
        ProjectApplication pa = ProjectApplication.builder()
            .project(project)
            .talent(talent)
            .coverLetter(coverLetter)
            .status(ProjectApplication.Status.PENDING)
            .build();
        applicationRepository.save(pa);
    }

    @Override
    public void withdrawApplication(String applicationId, String requesterUsername) {
        ProjectApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        if (requesterUsername == null || requesterUsername.isBlank()) {
            throw new AccessDeniedException("Unauthenticated user");
        }

        final String login = requesterUsername;
        User user = userRepository.findByUsername(login)
                .or(() -> userRepository.findByEmail(login))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Talent talent = talentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new AccessDeniedException("Talent profile not found"));

        if (application.getTalent() == null || application.getTalent().getId() == null
                || !application.getTalent().getId().equals(talent.getId())) {
            throw new AccessDeniedException("Not allowed to withdraw this application");
        }

        application.setStatus(ProjectApplication.Status.WITHDRAWN);
        applicationRepository.save(application);
    }

    @Override
    public List<ProjectApplicationResponse> getApplicationsByProject(String projectId) {
        return applicationRepository.findAll().stream()
                .filter(pa -> pa.getProject().getId().equals(projectId))
                .map(ProjectApplicationResponse::from)
                .toList();
    }

    @Override
    public List<ProjectApplicationResponse> getApplicationsByTalent(String talentId) {
        return applicationRepository.findAll().stream()
                .filter(pa -> pa.getTalent().getId().equals(talentId))
                .map(ProjectApplicationResponse::from)
                .toList();
    }
}
