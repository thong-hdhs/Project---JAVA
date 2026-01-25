package com.example.labOdc.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.Response.ProjectResponse;
import com.example.labOdc.DTO.Response.TalentResponse;
import com.example.labOdc.DTO.TalentDTO;
import com.example.labOdc.Model.ProjectApplication;
import com.example.labOdc.Model.Talent;
import com.example.labOdc.Service.TalentService;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@PermitAll
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/talents")
public class TalentController {
    private final TalentService talentService;

    /**
     * Chức năng: Tạo hồ sơ sinh viên mới.
     * Service: TalentService.createTalent() - Xử lý logic tạo và lưu entity.
     */
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('TALENT', 'SYSTEM_ADMIN')")
    public ApiResponse<TalentResponse> createTalent(@Valid @RequestBody TalentDTO talentDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ApiResponse.error(errorMessages);
        }
        TalentResponse response = talentService.createTalent(talentDTO);
        return ApiResponse.success(response, "Created", HttpStatus.CREATED);
    }

    /**
     * Chức năng: Lấy danh sách tất cả hồ sơ sinh viên.
     * Service: TalentService.getAllTalents() - Truy vấn và trả về list.
     */
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('LAB_ADMIN', 'MENTOR', 'SYSTEM_ADMIN')")
    public ApiResponse<List<TalentResponse>> getAllTalents() {
        List<TalentResponse> list = talentService.getAllTalents();
        return ApiResponse.success(list, "OK", HttpStatus.OK);
    }

    /**
     * Chức năng: Xóa hồ sơ sinh viên theo ID.
     * Service: TalentService.deleteTalent() - Xử lý xóa entity.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<?> deleteTalent(@PathVariable String id) {
        talentService.deleteTalent(id);
        return ResponseEntity.ok("Deleted");
    }

    /**
     * Chức năng: Lấy hồ sơ sinh viên theo ID.
     * Service: TalentService.getTalentById() - Truy vấn entity theo ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TALENT', 'LAB_ADMIN', 'MENTOR', 'SYSTEM_ADMIN')")
    public ApiResponse<TalentResponse> getTalentById(@PathVariable String id) {
        TalentResponse response = talentService.getTalentById(id);
        return ApiResponse.success(response, "OK", HttpStatus.OK);
    }

    /**
     * Chức năng: Cập nhật hồ sơ sinh viên theo ID.
     * Service: TalentService.updateTalent() - Xử lý cập nhật entity.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TALENT', 'SYSTEM_ADMIN')")
    public ApiResponse<TalentResponse> updateTalent(@Valid @RequestBody TalentDTO talentDTO, @PathVariable String id) {
        TalentResponse response = talentService.updateTalent(talentDTO, id);
        return ApiResponse.success(response, "Updated", HttpStatus.OK);
    }

    /**
     * Chức năng: Lọc danh sách sinh viên theo ngành học.
     * Service: TalentService.findByMajor() - Truy vấn theo major.
     */
    @GetMapping("/major/{major}")
    @PreAuthorize("hasAnyRole('LAB_ADMIN', 'MENTOR', 'SYSTEM_ADMIN')")
    public ApiResponse<List<TalentResponse>> getTalentsByMajor(@PathVariable String major) {
        List<TalentResponse> list = talentService.findByMajor(major);
        return ApiResponse.success(list, "OK", HttpStatus.OK);
    }

    /**
     * Chức năng: Lọc danh sách sinh viên theo trạng thái.
     * Service: TalentService.findByStatus() - Truy vấn theo status.
     */
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('LAB_ADMIN', 'MENTOR', 'SYSTEM_ADMIN')")
    public ApiResponse<List<TalentResponse>> getTalentsByStatus(@PathVariable Talent.Status status) {
        List<TalentResponse> list = talentService.findByStatus(status);
        return ApiResponse.success(list, "OK", HttpStatus.OK);
    }

    /**
     * Đặt trạng thái sẵn sàng của talent.
     * @param talentId ID talent
     * @param status Trạng thái
     */
    @PostMapping("/availability/{talentId}")
    @PreAuthorize("hasRole('TALENT')")
    public ApiResponse<String> setTalentAvailability(@PathVariable String talentId, @RequestParam Talent.Status status) {
        talentService.setTalentAvailability(talentId, status);
        return ApiResponse.success("Availability updated", "OK", HttpStatus.OK);
    }

    /**
     * Ứng tuyển vào dự án.
     * @param projectId ID dự án
     * @param talentId ID talent
     * @param coverLetter Thư xin việc
     * @return Kết quả ứng tuyển
     */
    @PostMapping("/apply/{projectId}/{talentId}")
    @PreAuthorize("hasRole('TALENT')")
    public ApiResponse<String> applyToProject(@PathVariable String projectId, @PathVariable String talentId, @RequestParam String coverLetter) {
        talentService.applyToProject(projectId, talentId, coverLetter);
        return ApiResponse.success("Application submitted successfully", "OK", HttpStatus.CREATED);
    }

    /**
     * Rút đơn ứng tuyển.
     * @param applicationId ID đơn ứng tuyển
     * @return Kết quả rút đơn
     */
    @PutMapping("/withdraw/{applicationId}")
    @PreAuthorize("hasRole('TALENT')")
    public ApiResponse<String> withdrawApplication(@PathVariable String applicationId) {
        talentService.withdrawApplication(applicationId);
        return ApiResponse.success("Application withdrawn successfully", "OK", HttpStatus.OK);
    }

    /**
     * Lấy danh sách đơn ứng tuyển của talent.
     * @param talentId ID talent
     * @return Danh sách đơn ứng tuyển
     */
    @GetMapping("/applications/{talentId}")
    @PreAuthorize("hasRole('TALENT')")
    public ApiResponse<List<ProjectApplication>> getMyApplications(@PathVariable String talentId) {
        List<ProjectApplication> applications = talentService.getMyApplications(talentId);
        return ApiResponse.success(applications, "Applications retrieved", HttpStatus.OK);
    }

    /**
     * Lấy danh sách dự án của talent.
     * @param talentId ID talent
     * @return Danh sách dự án
     */
    @GetMapping("/projects/{talentId}")
    @PreAuthorize("hasRole('TALENT')")
    public ApiResponse<List<ProjectResponse>> getMyProjects(@PathVariable String talentId) {
        List<ProjectResponse> projects = talentService.getMyProjects(talentId);
        return ApiResponse.success(projects, "Projects retrieved", HttpStatus.OK);
    }

    /**
     * Lấy danh sách task được giao.
     * @param talentId ID talent
     * @return Danh sách task
     */
    @GetMapping("/tasks/{talentId}")
    @PreAuthorize("hasRole('TALENT')")
    public ApiResponse<List<com.example.labOdc.Model.Task>> getAssignedTasks(@PathVariable String talentId) {
        List<com.example.labOdc.Model.Task> tasks = talentService.getAssignedTasks(talentId);
        return ApiResponse.success(tasks, "Tasks retrieved", HttpStatus.OK);
    }

    /**
     * Cập nhật kỹ năng và chứng chỉ.
     * @param talentId ID talent
     */
    @PutMapping("/skills/{talentId}")
    @PreAuthorize("hasRole('TALENT')")
    public ApiResponse<String> updateSkillsAndCertifications(@PathVariable String talentId) {
        talentService.updateSkillsAndCertifications(talentId);
        return ApiResponse.success("Skills and certifications updated", "OK", HttpStatus.OK);
    }

    /**
     * Cập nhật tiến độ task.
     * @param taskId ID task
     * @param status Trạng thái mới
     */
    @PutMapping("/tasks/{taskId}/progress")
    @PreAuthorize("hasRole('TALENT')")
    public ApiResponse<String> updateTaskProgress(@PathVariable String taskId, @RequestParam String status) {
        talentService.updateTaskProgress(taskId, status);
        return ApiResponse.success("Task progress updated", "OK", HttpStatus.OK);
    }

    /**
     * Gửi đóng góp dự án.
     * @param projectId ID dự án
     * @param contributionRequest Nội dung đóng góp
     */
    @PostMapping("/contributions/{projectId}")
    @PreAuthorize("hasRole('TALENT')")
    public ApiResponse<String> submitContribution(@PathVariable String projectId, @RequestBody String contributionRequest) {
        talentService.submitContribution(projectId, contributionRequest);
        return ApiResponse.success("Contribution submitted", "OK", HttpStatus.OK);
    }

    /**
     * Vote cho đề xuất.
     * @param projectId ID dự án
     * @param voteRequest Nội dung vote
     */
    @PostMapping("/votes/{projectId}")
    @PreAuthorize("hasRole('TALENT')")
    public ApiResponse<String> voteOnProposal(@PathVariable String projectId, @RequestBody String voteRequest) {
        talentService.voteOnProposal(projectId, voteRequest);
        return ApiResponse.success("Vote submitted", "OK", HttpStatus.OK);
    }

    /**
     * Xem phân bổ quỹ team.
     * @param projectId ID dự án
     */
    @GetMapping("/funds/{projectId}")
    @PreAuthorize("hasRole('TALENT')")
    public ApiResponse<String> viewTeamFundDistribution(@PathVariable String projectId) {
        talentService.viewTeamFundDistribution(projectId);
        return ApiResponse.success("Fund distribution viewed", "OK", HttpStatus.OK);
    }
}
