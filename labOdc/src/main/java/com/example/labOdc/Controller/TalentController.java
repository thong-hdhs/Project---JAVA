package com.example.labOdc.Controller;

import java.math.BigDecimal;
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
import com.example.labOdc.DTO.Response.TalentResponse;
import com.example.labOdc.DTO.TalentDTO;
import com.example.labOdc.Model.Talent;
import com.example.labOdc.Service.TalentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

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


}
