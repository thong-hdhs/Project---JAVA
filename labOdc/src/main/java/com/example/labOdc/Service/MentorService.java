package com.example.labOdc.Service;

import java.math.BigDecimal;
import java.util.List;

import com.example.labOdc.DTO.MentorDTO;
import com.example.labOdc.DTO.Response.MentorInvitationResponse;
import com.example.labOdc.DTO.Response.MentorResponse;
import com.example.labOdc.DTO.Response.ProjectResponse;
import com.example.labOdc.Model.Mentor;

public interface MentorService {
    MentorResponse createMentor(MentorDTO mentorDTO);

    List<MentorResponse> getAllMentors();

    void deleteMentor(String id);

    MentorResponse getMentorById(String id);

    MentorResponse updateMentor(MentorDTO mentorDTO, String id);

    List<MentorResponse> findByStatus(Mentor.Status status);

    List<MentorResponse> findByRatingGreaterThanEqual(BigDecimal rating);

    /**
     * Chức năng: Chấp nhận lời mời làm mentor cho dự án.
     * Service: MentorService.acceptInvite() - Xử lý chấp nhận và cập nhật trạng thái.
     */
    void acceptInvite(String inviteId);

    /**
     * Chức năng: Từ chối lời mời làm mentor cho dự án.
     * Service: MentorService.rejectInvite() - Xử lý từ chối và ghi nhận lý do.
     */
    void rejectInvite(String inviteId, String reason);


    /**
     * Đặt trạng thái sẵn sàng của mentor.
     * @param mentorId ID mentor
     * @param status Trạng thái
     */
    void setMentorAvailability(String mentorId, Mentor.Status status);

    /**
     * Lấy danh sách lời mời mentor.
     * @param mentorId ID mentor
     * @return Danh sách lời mời
     */
    List<MentorInvitationResponse> getMentorInvitations(String mentorId);

    /**
     * Lấy danh sách lời mời gửi cho mentor đang đăng nhập (từ SecurityContext).
     * @return Danh sách lời mời sắp xếp theo thời gian tạo mới nhất
     */
    List<MentorInvitationResponse> getMyMentorInvitations();

    /**
     * Lấy danh sách dự án được giao.
     * @param mentorId ID mentor
     * @return Danh sách dự án
     */
    List<ProjectResponse> getAssignedProjects(String mentorId);

    /**
     * Lấy danh sách dự án được giao cho mentor đang đăng nhập.
     */
    List<ProjectResponse> getMyAssignedProjects();

    /**
     * Phân tích nhiệm vụ từ template Excel.
     * @param projectId ID dự án
     * @param excelTemplate Template Excel
     */
    void breakdownTasks(String projectId, String excelTemplate);

    /**
     * Giao nhiệm vụ cho talent.
     * @param taskId ID nhiệm vụ
     * @param talentId ID talent
     */
    void assignTask(String taskId, String talentId);

    /**
     * Cập nhật trạng thái nhiệm vụ.
     * @param taskId ID nhiệm vụ
     * @param status Trạng thái mới
     */
    void updateTaskStatus(String taskId, String status);

    /**
     * Gửi báo cáo dự án.
     * @param projectId ID dự án
     * @param reportRequest Nội dung báo cáo
     */
    void submitReport(String projectId, String reportRequest);

    /**
     * Đánh giá talent.
     * @param projectId ID dự án
     * @param talentId ID talent
     * @param evaluationRequest Nội dung đánh giá
     */
    void evaluateTalent(String projectId, String talentId, String evaluationRequest);

    /**
     * Phê duyệt phân bổ quỹ.
     * @param projectId ID dự án
     */
    void approveFundDistribution(String projectId);
}
