package com.example.labOdc.Service;

import java.math.BigDecimal;
import java.util.List;

import com.example.labOdc.DTO.ProjectApplicationDTO;
import com.example.labOdc.DTO.Response.ProjectApplicationResponse;

public interface ProjectApplicationService {
    ProjectApplicationResponse createApplication(ProjectApplicationDTO dto);

    List<ProjectApplicationResponse> getAllApplications();

    ProjectApplicationResponse getApplicationById(String id);

    void deleteApplication(String id);

    ProjectApplicationResponse updateApplication(ProjectApplicationDTO dto, String id);

    List<ProjectApplicationResponse> findByProjectId(String projectId);

    List<ProjectApplicationResponse> findByTalentId(String talentId);

    /**
     * Chức năng: Phê duyệt đơn ứng tuyển dự án.
     * Service: Cập nhật trạng thái thành APPROVED, ghi nhận người phê duyệt và thời gian.
     */
    ProjectApplicationResponse approveApplication(String id, String reviewerId);

    /**
     * Chức năng: Từ chối đơn ứng tuyển dự án.
     * Service: Cập nhật trạng thái thành REJECTED, ghi nhận lý do từ chối và người phê duyệt.
     */
    ProjectApplicationResponse rejectApplication(String id, String reviewerId, String reason);

    /**
     * Chức năng: Hủy đơn ứng tuyển dự án.
     * Service: Cập nhật trạng thái thành WITHDRAWN.
     */
    ProjectApplicationResponse cancelApplication(String id);
}
