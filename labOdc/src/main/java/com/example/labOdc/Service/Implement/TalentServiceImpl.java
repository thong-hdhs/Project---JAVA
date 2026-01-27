package com.example.labOdc.Service.Implement;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.Response.ProjectResponse;
import com.example.labOdc.DTO.Response.TalentResponse;
import com.example.labOdc.DTO.TalentDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.FundDistribution;
import com.example.labOdc.Model.Project;
import com.example.labOdc.Model.ProjectApplication;
import com.example.labOdc.Model.RoleEntity;
import com.example.labOdc.Model.Talent;
import com.example.labOdc.Model.Task;
import com.example.labOdc.Model.User;
import com.example.labOdc.Model.UserRole;
import com.example.labOdc.Repository.FundAllocationRepository;
import com.example.labOdc.Repository.FundDistributionRepository;
import com.example.labOdc.Repository.ProjectApplicationRepository;
import com.example.labOdc.Repository.ProjectRepository;
import com.example.labOdc.Repository.ProjectTeamRepository;
import com.example.labOdc.Repository.RoleRepository;
import com.example.labOdc.Repository.TalentRepository;
import com.example.labOdc.Repository.TaskRepository;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.TalentService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TalentServiceImpl implements TalentService {

    private static final Logger logger = LoggerFactory.getLogger(TalentServiceImpl.class);
    private final TalentRepository talentRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProjectRepository projectRepository;
    private final ProjectApplicationRepository projectApplicationRepository;
    private final ProjectTeamRepository projectTeamRepository;
    private final TaskRepository taskRepository;
    private final FundAllocationRepository fundAllocationRepository;
    private final FundDistributionRepository fundDistributionRepository;

   @Override
@Transactional
public TalentResponse createTalent(TalentDTO talentDTO) {
    logger.info("Create or update talent");

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
        throw new RuntimeException("Unauthenticated user");
    }

    String username = auth.getName();
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    // ✅ KIỂM TRA TALENT ĐÃ TỒN TẠI CHƯA
    Talent talent = talentRepository.findByUserId(user.getId())
            .orElseGet(() -> Talent.builder()
                    .user(user)
                    .status(Talent.Status.AVAILABLE)
                    .build()
            );

    // ✅ SET FIELD (UPDATE / CREATE đều dùng chung)
    talent.setStudentCode(talentDTO.getStudentCode());
    talent.setMajor(talentDTO.getMajor());
    talent.setYear(talentDTO.getYear());
    talent.setSkills(talentDTO.getSkills());
    talent.setCertifications(talentDTO.getCertifications());
    talent.setPortfolioUrl(talentDTO.getPortfolioUrl());
    talent.setGithubUrl(talentDTO.getGithubUrl());
    talent.setLinkedinUrl(talentDTO.getLinkedinUrl());

    Talent savedTalent = talentRepository.save(talent);

    // ✅ GÁN ROLE TALENT (GIỮ NGUYÊN LOGIC CŨ – ĐÚNG)
    RoleEntity talentRole = roleRepository.findByRole(UserRole.TALENT)
            .orElseThrow(() -> new ResourceNotFoundException("TALENT role not found"));

    boolean hasTalentRole = user.getRoles().stream()
            .anyMatch(r -> r.getRole() == UserRole.TALENT);

    if (!hasTalentRole) {
        user.getRoles().add(talentRole);
        userRepository.save(user);
    }

    return TalentResponse.fromTalent(savedTalent);
}

    /**
     * Chức năng: Lấy danh sách tất cả sinh viên.
     * Repository: TalentRepository.findAll() - Truy vấn tất cả entities.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<TalentResponse> getAllTalents() {
        logger.debug("Fetching all talents");
        return talentRepository.findAll().stream()
                .map(TalentResponse::fromTalent)
                .toList();
    }

    /**
     * Chức năng: Xóa sinh viên theo ID.
     * Repository: TalentRepository.findById() và delete() - Tìm và xóa entity.
     */
    @Override
    @Transactional
    public void deleteTalent(String id) {
        logger.info("Deleting talent with ID: {}", id);
        Talent talent = talentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));
        talentRepository.delete(talent);
        logger.info("Talent deleted successfully");
    }

    /**
     * Chức năng: Lấy sinh viên theo ID.
     * Repository: TalentRepository.findById() - Truy vấn entity theo ID.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public TalentResponse getTalentById(String id) {
        logger.debug("Fetching talent with ID: {}", id);
        Talent talent = talentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));
        return TalentResponse.fromTalent(talent);
    }

    /**
     * Chức năng: Cập nhật sinh viên theo ID.
     * Repository: TalentRepository.findById() và save() - Tìm và cập nhật entity.
     */
    @Override
    @Transactional
    public TalentResponse updateTalent(TalentDTO talentDTO, String id) {
        logger.info("Updating talent with ID: {}", id);
        Talent talent = talentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));

        // Update only non-null fields
        updateTalentFields(talent, talentDTO);

        Talent updatedTalent = talentRepository.save(talent);
        logger.info("Talent updated successfully");
        return TalentResponse.fromTalent(updatedTalent);
    }

    /**
     * Helper method to update talent fields from DTO
     * Only updates non-null fields to support partial updates
     */
    private void updateTalentFields(Talent talent, TalentDTO dto) {
        if (dto.getStudentCode() != null)
            talent.setStudentCode(dto.getStudentCode());
        if (dto.getMajor() != null)
            talent.setMajor(dto.getMajor());
        if (dto.getYear() != null)
            talent.setYear(dto.getYear());
        if (dto.getSkills() != null)
            talent.setSkills(dto.getSkills());
        if (dto.getCertifications() != null)
            talent.setCertifications(dto.getCertifications());
        if (dto.getPortfolioUrl() != null)
            talent.setPortfolioUrl(dto.getPortfolioUrl());
        if (dto.getGithubUrl() != null)
            talent.setGithubUrl(dto.getGithubUrl());
        if (dto.getLinkedinUrl() != null)
            talent.setLinkedinUrl(dto.getLinkedinUrl());
    }

    /**
     * Chức năng: Lọc danh sách sinh viên theo ngành học.
     * Repository: TalentRepository.findByMajor() - Truy vấn theo major.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<TalentResponse> findByMajor(String major) {
        logger.debug("Finding talents by major: {}", major);
        return talentRepository.findByMajor(major).stream()
                .map(TalentResponse::fromTalent)
                .toList();
    }

    /**
     * Chức năng: Lọc danh sách sinh viên theo trạng thái.
     * Repository: TalentRepository.findByStatus() - Truy vấn theo status.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<TalentResponse> findByStatus(Talent.Status status) {
        logger.debug("Finding talents by status: {}", status);
        return talentRepository.findByStatus(status).stream()
                .map(TalentResponse::fromTalent)
                .toList();
    }

    @Override
    public void setTalentAvailability(String talentId, Talent.Status status) {
        Talent talent = talentRepository.findById(talentId)
                .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));
        talent.setStatus(status);
        talentRepository.save(talent);
    }

    @Override
    public void applyToProject(String projectId, String talentId, String coverLetter) {
        Talent talent = talentRepository.findById(talentId)
                .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        
        ProjectApplication application = ProjectApplication.builder()
                .project(project)
                .talent(talent)
                .coverLetter(coverLetter)
                .status(ProjectApplication.Status.PENDING)
                .build();
        
        projectApplicationRepository.save(application);
    }

    @Override
    public void withdrawApplication(String applicationId) {
        ProjectApplication application = projectApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        application.setStatus(ProjectApplication.Status.WITHDRAWN);
        projectApplicationRepository.save(application);
    }

    @Override
    public List<ProjectApplication> getMyApplications(String talentId) {
        Talent talent = talentRepository.findById(talentId)
                .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));
        return projectApplicationRepository.findAll().stream()
                .filter(pa -> pa.getTalent().getId().equals(talentId))
                .toList();
    }

    @Override
    public List<ProjectResponse> getMyProjects(String talentId) {
        Talent talent = talentRepository.findById(talentId)
                .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));
        return projectTeamRepository.findAll().stream()
                .filter(pt -> pt.getTalent().getId().equals(talentId))
                .map(pt -> ProjectResponse.fromProject(pt.getProject()))
                .toList();
    }

    @Override
    public List<Task> getAssignedTasks(String talentId) {
        Talent talent = talentRepository.findById(talentId)
                .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));
        return taskRepository.findAll().stream()
                .filter(task -> talentId.equals(task.getAssignedTo()))
                .toList();
    }

    @Override
    public void updateSkillsAndCertifications(String talentId) {
        // Placeholder: Cập nhật skills và certifications
        System.out.println("Updating skills and certifications for talent: " + talentId);
    }

    @Override
    public void updateTaskProgress(String taskId, String status) {
        logger.info("Updating task progress for taskId: {} to status: {}", taskId, status);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        
        try {
            Task.Status taskStatus = Task.Status.valueOf(status.toUpperCase());
            task.setStatus(taskStatus);
            if (taskStatus == Task.Status.DONE) {
                task.setCompletedDate(java.time.LocalDate.now());
            }
            taskRepository.save(task);
            logger.info("Task progress updated successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid status: {}", status);
            throw new IllegalArgumentException("Invalid task status: " + status);
        }
    }

    @Override
    public void submitContribution(String projectId, String contributionRequest) {
        // Placeholder: Gửi contribution
        System.out.println("Submitting contribution for project: " + projectId + " with: " + contributionRequest);
    }

    @Override
    public void voteOnProposal(String projectId, String voteRequest) {
        // Placeholder: Vote proposal
        System.out.println("Voting on proposal for project: " + projectId + " with: " + voteRequest);
    }

    @Override
    public void viewTeamFundDistribution(String projectId) {
        logger.info("Viewing team fund distribution for projectId: {}", projectId);
        
        // Find fund allocation for the project
        fundAllocationRepository.findByProjectId(projectId).ifPresentOrElse(fundAllocation -> {
            // Find all fund distributions for this allocation
            List<FundDistribution> distributions = fundDistributionRepository.findByFundAllocationId(fundAllocation.getId());
            
            if (distributions.isEmpty()) {
                logger.info("No fund distributions found for project: {}", projectId);
            } else {
                logger.info("Fund distributions for project {}:", projectId);
                distributions.forEach(dist -> {
                    logger.info("Talent: {}, Amount: {}, Status: {}", 
                        dist.getTalent().getUser().getUsername(), 
                        dist.getAmount(), 
                        dist.getStatus());
                });
            }
        }, () -> {
            logger.info("No fund allocation found for project: {}", projectId);
        });
    }
}
