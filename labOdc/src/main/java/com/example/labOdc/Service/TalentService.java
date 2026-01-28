package com.example.labOdc.Service;

import java.util.List;

import com.example.labOdc.DTO.Response.ProjectResponse;
import com.example.labOdc.DTO.Response.TalentResponse;
import com.example.labOdc.DTO.TalentDTO;
import com.example.labOdc.Model.ProjectApplication;
import com.example.labOdc.Model.Talent;

public interface TalentService {
    TalentResponse createTalent(TalentDTO talentDTO);

    /**
     * Lấy hồ sơ Talent của chính user đang đăng nhập (từ JWT).
     */
    TalentResponse getMyProfile();

    /**
     * Cập nhật hồ sơ Talent của chính user đang đăng nhập (từ JWT).
     * FE không được truyền userId/talentId.
     */
    TalentResponse updateMyProfile(TalentDTO talentDTO);

    List<TalentResponse> getAllTalents();

    void deleteTalent(String id);

    TalentResponse getTalentById(String id);

    TalentResponse updateTalent(TalentDTO talentDTO, String id);

    List<TalentResponse> findByMajor(String major);

    List<TalentResponse> findByStatus(Talent.Status status);

    

    /**
     * Đặt trạng thái sẵn sàng của talent.
     * @param talentId ID talent
     * @param status Trạng thái
     */
    void setTalentAvailability(String talentId, Talent.Status status);

    /**
     * Ứng tuyển vào dự án.
     * @param projectId ID dự án
     * @param talentId ID talent
     * @param coverLetter Thư xin việc
     */
    void applyToProject(String projectId, String talentId, String coverLetter);

    /**
     * Rút đơn ứng tuyển.
     * @param applicationId ID đơn ứng tuyển
     */
    void withdrawApplication(String applicationId);

    /**
     * Lấy danh sách đơn ứng tuyển của talent.
     * @param talentId ID talent
     * @return Danh sách đơn ứng tuyển
     */
    List<ProjectApplication> getMyApplications(String talentId);

    /**
     * Lấy danh sách dự án của talent.
     * @param talentId ID talent
     * @return Danh sách dự án
     */
    List<ProjectResponse> getMyProjects(String talentId);

    /**
     * Lấy danh sách task được giao cho talent.
     * @param talentId ID talent
     * @return Danh sách task
     */
    List<com.example.labOdc.Model.Task> getAssignedTasks(String talentId);

    /**
     * Cập nhật kỹ năng và chứng chỉ.
     * @param talentId ID talent
     */
    void updateSkillsAndCertifications(String talentId);

    /**
     * Cập nhật tiến độ task.
     * @param taskId ID task
     * @param status Trạng thái mới
     */
    void updateTaskProgress(String taskId, String status);

    /**
     * Gửi đóng góp dự án.
     * @param projectId ID dự án
     * @param contributionRequest Nội dung đóng góp
     */
    void submitContribution(String projectId, String contributionRequest);

    /**
     * Vote cho đề xuất.
     * @param projectId ID dự án
     * @param voteRequest Nội dung vote
     */
    void voteOnProposal(String projectId, String voteRequest);

    /**
     * Xem phân bổ quỹ team.
     * @param projectId ID dự án
     */
    void viewTeamFundDistribution(String projectId);
}
