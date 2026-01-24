package com.example.labOdc.Controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
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
import com.example.labOdc.DTO.MentorDTO;
import com.example.labOdc.DTO.Response.MentorResponse;
import com.example.labOdc.Model.Mentor;
import com.example.labOdc.Service.MentorService;

@RestController
@RequestMapping("api/v1/mentors")
public class MentorController {
    private final MentorService mentorService;

    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    /**
     * Chức năng: Tạo hồ sơ Mentor mới.
     * Service: MentorService.createMentor() - Xử lý logic tạo và lưu entity.
     */
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('MENTOR', 'SYSTEM_ADMIN')")
    public ApiResponse<MentorResponse> createMentor(@Validated @RequestBody MentorDTO mentorDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ApiResponse.error(errorMessages);
        }
        MentorResponse response = mentorService.createMentor(mentorDTO);
        return ApiResponse.success(response, "Created", HttpStatus.CREATED);
    }

    /**
     * Chức năng: Lấy danh sách tất cả Mentors.
     * Service: MentorService.getAllMentors() - Truy vấn và trả về list.
     */
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('LAB_ADMIN', 'COMPANY', 'SYSTEM_ADMIN')")
    public ApiResponse<List<MentorResponse>> getAllMentors() {
        List<MentorResponse> list = mentorService.getAllMentors();
        return ApiResponse.success(list, "OK", HttpStatus.OK);
    }

    /**
     * Chức năng: Xóa Mentor theo ID.
     * Service: MentorService.deleteMentor() - Xử lý xóa entity.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<?> deleteMentor(@PathVariable String id) {
        mentorService.deleteMentor(id);
        return ResponseEntity.ok("Deleted");
    }

    /**
     * Chức năng: Lấy Mentor theo ID.
     * Service: MentorService.getMentorById() - Truy vấn entity theo ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MENTOR', 'LAB_ADMIN', 'COMPANY', 'SYSTEM_ADMIN')")
    public ApiResponse<MentorResponse> getMentorById(@PathVariable String id) {
        MentorResponse response = mentorService.getMentorById(id);
        return ApiResponse.success(response, "OK", HttpStatus.OK);
    }

    /**
     * Chức năng: Cập nhật Mentor theo ID.
     * Service: MentorService.updateMentor() - Xử lý cập nhật entity.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MENTOR', 'SYSTEM_ADMIN')")
    public ApiResponse<MentorResponse> updateMentor(@Validated @RequestBody MentorDTO mentorDTO, @PathVariable String id) {
        MentorResponse response = mentorService.updateMentor(mentorDTO, id);
        return ApiResponse.success(response, "Updated", HttpStatus.OK);
    }

    /**
     * Chức năng: Lọc danh sách Mentors theo trạng thái.
     * Service: MentorService.findByStatus() - Truy vấn theo status.
     */
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('LAB_ADMIN', 'SYSTEM_ADMIN')")
    public ApiResponse<List<MentorResponse>> getMentorsByStatus(@PathVariable Mentor.Status status) {
        List<MentorResponse> list = mentorService.findByStatus(status);
        return ApiResponse.success(list, "OK", HttpStatus.OK);
    }

    /**
     * Chức năng: Lọc danh sách Mentors theo rating tối thiểu.
     * Service: MentorService.findByRatingGreaterThanEqual() - Truy vấn theo rating.
     */
    @GetMapping("/rating/{minRating}")
    @PreAuthorize("hasAnyRole('LAB_ADMIN', 'COMPANY', 'SYSTEM_ADMIN')")
    public ApiResponse<List<MentorResponse>> getMentorsByMinRating(@PathVariable BigDecimal minRating) {
        List<MentorResponse> list = mentorService.findByRatingGreaterThanEqual(minRating);
        return ApiResponse.success(list, "OK", HttpStatus.OK);
    }

    /**
     * Chức năng: Chấp nhận lời mời làm mentor cho dự án.
     * Service: MentorService.acceptInvite() - Xử lý chấp nhận lời mời.
     */
    @PostMapping("/accept-invite/{inviteId}")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<?> acceptInvite(@PathVariable String inviteId) {
        mentorService.acceptInvite(inviteId);
        return ResponseEntity.ok("Invite accepted");
    }

    /**
     * Chức năng: Từ chối lời mời làm mentor cho dự án.
     * Service: MentorService.rejectInvite() - Xử lý từ chối lời mời.
     */
    @PostMapping("/reject-invite/{inviteId}")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<?> rejectInvite(@PathVariable String inviteId, @RequestParam String reason) {
        mentorService.rejectInvite(inviteId, reason);
        return ResponseEntity.ok("Invite rejected");
    }

    
}
