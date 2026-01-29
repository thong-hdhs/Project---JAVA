package com.example.labOdc.Service.Implement;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.MentorDTO;
import com.example.labOdc.DTO.Response.MentorInvitationResponse;
import com.example.labOdc.DTO.Response.MentorResponse;
import com.example.labOdc.DTO.Response.ProjectResponse;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Mentor;
import com.example.labOdc.Model.MentorInvitation;
import com.example.labOdc.Model.MentorInvitationStatus;
import com.example.labOdc.Model.RoleEntity;
import com.example.labOdc.Model.User;
import com.example.labOdc.Model.UserRole;
import com.example.labOdc.Repository.MentorInvitationRepository;
import com.example.labOdc.Repository.MentorRepository;
import com.example.labOdc.Repository.ProjectMentorRepository;
import com.example.labOdc.Repository.RoleRepository;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.MentorService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MentorInvitationRepository mentorInvitationRepository;
    private final ProjectMentorRepository projectMentorRepository;

    @Override
@Transactional
public MentorResponse createMentor(MentorDTO mentorDTO) {

    // 1. Lấy user đang đăng nhập
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
        throw new RuntimeException("Unauthenticated user");
    }

    String username = auth.getName();

    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    // 2. Chặn tạo Mentor trùng cho cùng user
    if (mentorRepository.existsByUserId(user.getId())) {
        throw new RuntimeException("User already has Mentor profile");
    }

    // 3. Tạo Mentor
    Mentor mentor = Mentor.builder()
            .expertise(mentorDTO.getExpertise())
            .yearsExperience(mentorDTO.getYearsExperience())
            .bio(mentorDTO.getBio())
            .user(user)
            .status(Mentor.Status.AVAILABLE)
            .build();

    Mentor savedMentor = mentorRepository.save(mentor);

    // 4. Gán role MENTOR cho user (nếu chưa có)
    RoleEntity mentorRole = roleRepository.findByRole(UserRole.MENTOR)
            .orElseThrow(() -> new ResourceNotFoundException("MENTOR role not found"));

    boolean hasMentorRole = user.getRoles().stream()
            .anyMatch(r -> r.getRole() == UserRole.MENTOR);

    if (!hasMentorRole) {
        user.getRoles().add(mentorRole);
        userRepository.save(user);
    }

    return MentorResponse.fromMentor(savedMentor);
}
    /**
     * Chức năng: Lấy danh sách tất cả Mentors.
     * Repository: MentorRepository.findAll() - Truy vấn tất cả entities.
     */
    @Override
    public List<MentorResponse> getAllMentors() {
        return mentorRepository.findAll().stream()
                .map(MentorResponse::fromMentor)
                .toList();
    }

    /**
     * Chức năng: Xóa Mentor theo ID.
     * Repository: MentorRepository.deleteById() - Xóa entity theo ID.
     */
    @Override
    public void deleteMentor(String id) {
        mentorRepository.deleteById(id);
    }

    /**
     * Chức năng: Lấy Mentor theo ID.
     * Repository: MentorRepository.findById() - Truy vấn entity theo ID.
     */
    @Override
    public MentorResponse getMentorById(String id) {
        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));
        return MentorResponse.fromMentor(mentor);
    }

    /**
     * Chức năng: Cập nhật Mentor theo ID.
     * Repository: MentorRepository.findById() và save() - Tìm và cập nhật entity.
     */
    @Override
    public MentorResponse updateMentor(MentorDTO mentorDTO, String id) {
        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));

        if (mentorDTO.getExpertise() != null)
            mentor.setExpertise(mentorDTO.getExpertise());
        if (mentorDTO.getYearsExperience() != null)
            mentor.setYearsExperience(mentorDTO.getYearsExperience());
        if (mentorDTO.getBio() != null)
            mentor.setBio(mentorDTO.getBio());

        Mentor updatedMentor = mentorRepository.save(mentor);
        return MentorResponse.fromMentor(updatedMentor);
    }

    /**
     * Chức năng: Lọc danh sách Mentors theo trạng thái.
     * Repository: MentorRepository.findByStatus() - Truy vấn theo status.
     */
    @Override
    public List<MentorResponse> findByStatus(Mentor.Status status) {
        return mentorRepository.findByStatus(status).stream()
                .map(MentorResponse::fromMentor)
                .toList();
    }

    /**
     * Chức năng: Lọc danh sách Mentors theo rating tối thiểu.
     * Repository: MentorRepository.findByRatingGreaterThanEqual() - Truy vấn theo rating.
     */
    @Override
    public List<MentorResponse> findByRatingGreaterThanEqual(BigDecimal rating) {
        return mentorRepository.findByRatingGreaterThanEqual(rating).stream()
                .map(MentorResponse::fromMentor)
                .toList();
    }

    /**
     * Chức năng: Chấp nhận lời mời làm mentor cho dự án.
     * - Verify lời mời thuộc về mentor hiện tại
     * - Cập nhật status thành ACCEPTED
     * - Ghi nhận thời gian phản hồi
     */
    @Override
    @Transactional
    public void acceptInvite(String inviteId) {
        MentorInvitation invite = mentorInvitationRepository.findById(inviteId)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor invitation not found"));

        // Verify the invitation belongs to the authenticated mentor
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Unauthenticated user");
        }

        String username = auth.getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Mentor currentMentor = mentorRepository.findAll().stream()
                .filter(m -> m.getUser() != null && m.getUser().getId().equals(currentUser.getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Mentor profile not found for this user"));

        if (!invite.getMentor().getId().equals(currentMentor.getId())) {
            throw new RuntimeException("This invitation does not belong to you");
        }

        if (invite.getStatus() != MentorInvitationStatus.PENDING) {
            throw new RuntimeException("Only PENDING invitation can be accepted. Current status: " + invite.getStatus());
        }

        invite.setStatus(MentorInvitationStatus.ACCEPTED);
        invite.setRespondedAt(java.time.LocalDateTime.now());
        mentorInvitationRepository.save(invite);
    }

    /**
     * Chức năng: Chấp nhận lời mời và trả về entity (dùng cho controller).
     */
    @Transactional
    public MentorInvitation acceptInviteAndReturn(String inviteId) {
        acceptInvite(inviteId);
        return mentorInvitationRepository.findById(inviteId)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor invitation not found"));
    }

    /**
     * Chức năng: Từ chối lời mời làm mentor cho dự án.
     * - Verify lời mời thuộc về mentor hiện tại
     * - Cập nhật status thành REJECTED
     * - Ghi nhận thời gian phản hồi
     * TODO: Thêm rejectionReason field vào MentorInvitation entity nếu cần lưu lý do
     */
    @Override
    @Transactional
    public void rejectInvite(String inviteId, String reason) {
        MentorInvitation invite = mentorInvitationRepository.findById(inviteId)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor invitation not found"));

        // Verify the invitation belongs to the authenticated mentor
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Unauthenticated user");
        }

        String username = auth.getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Mentor currentMentor = mentorRepository.findAll().stream()
                .filter(m -> m.getUser() != null && m.getUser().getId().equals(currentUser.getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Mentor profile not found for this user"));

        if (!invite.getMentor().getId().equals(currentMentor.getId())) {
            throw new RuntimeException("This invitation does not belong to you");
        }

        if (invite.getStatus() != MentorInvitationStatus.PENDING) {
            throw new RuntimeException("Only PENDING invitation can be rejected. Current status: " + invite.getStatus());
        }

        invite.setStatus(MentorInvitationStatus.REJECTED);
        invite.setRespondedAt(java.time.LocalDateTime.now());
        mentorInvitationRepository.save(invite);
    }

    /**
     * Chức năng: Từ chối lời mời và trả về entity (dùng cho controller).
     */
    @Transactional
    public MentorInvitation rejectInviteAndReturn(String inviteId, String reason) {
        rejectInvite(inviteId, reason);
        return mentorInvitationRepository.findById(inviteId)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor invitation not found"));
    }


    @Override
    public void setMentorAvailability(String mentorId, Mentor.Status status) {
        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));
        mentor.setStatus(status);
        mentorRepository.save(mentor);
    }

    @Override
    public List<MentorInvitationResponse> getMentorInvitations(String mentorId) {
        // Validate mentor exists
        mentorRepository.findById(mentorId)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));
        // Use optimized query instead of findAll().filter()
        return mentorInvitationRepository.findByMentorIdOrderByCreatedAtDesc(mentorId)
                .stream()
                .map(MentorInvitationResponse::fromMentorInvitation)
                .toList();
    }

    /**
     * Lấy danh sách lời mời của mentor hiện tại (từ SecurityContext).
     * Sắp xếp theo thời gian tạo mới nhất.
     */
    @Override
    public List<MentorInvitationResponse> getMyMentorInvitations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName() == null || auth.getName().isBlank()) {
            throw new RuntimeException("Unauthenticated user");
        }

        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Mentor mentor = mentorRepository.findAll().stream()
                .filter(m -> m.getUser() != null && m.getUser().getId().equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Mentor profile not found for this user"));

        return getMentorInvitations(mentor.getId());
    }

    @Override
    public List<ProjectResponse> getAssignedProjects(String mentorId) {
        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));
        return projectMentorRepository.findAll().stream()
                .filter(pm -> pm.getMentor().getId().equals(mentorId))
                .map(pm -> ProjectResponse.fromProject(pm.getProject()))
                .toList();
    }

        @Override
        public List<ProjectResponse> getMyAssignedProjects() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName() == null || auth.getName().isBlank()) {
            throw new RuntimeException("Unauthenticated user");
        }

        String username = auth.getName();

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Mentor mentor = mentorRepository.findAll().stream()
            .filter(m -> m.getUser() != null && String.valueOf(m.getUser().getId()).equals(String.valueOf(user.getId())))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));

        return getAssignedProjects(mentor.getId());
        }

    @Override
    public void breakdownTasks(String projectId, String excelTemplate) {
        // Placeholder: Phân tích template Excel và tạo tasks
        System.out.println("Breaking down tasks for project: " + projectId + " with template: " + excelTemplate);
    }

    @Override
    public void assignTask(String taskId, String talentId) {
        // Placeholder: Giao task cho talent
        System.out.println("Assigning task: " + taskId + " to talent: " + talentId);
    }

    @Override
    public void updateTaskStatus(String taskId, String status) {
        // Placeholder: Cập nhật status task
        System.out.println("Updating task: " + taskId + " to status: " + status);
    }

    @Override
    public void submitReport(String projectId, String reportRequest) {
        // Placeholder: Gửi báo cáo
        System.out.println("Submitting report for project: " + projectId + " with content: " + reportRequest);
    }

    @Override
    public void evaluateTalent(String projectId, String talentId, String evaluationRequest) {
        // Placeholder: Đánh giá talent
        System.out.println("Evaluating talent: " + talentId + " in project: " + projectId + " with: " + evaluationRequest);
    }

    @Override
    public void approveFundDistribution(String projectId) {
        // Placeholder: Phê duyệt phân bổ quỹ
        System.out.println("Approving fund distribution for project: " + projectId);
    }
}