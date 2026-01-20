package com.example.labOdc.Controller;


import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.MemberContributionDTO;
import com.example.labOdc.DTO.Response.MemberContributionResponse;
import com.example.labOdc.Model.MemberContribution;
import com.example.labOdc.Service.MemberContributionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membercontributions")
@AllArgsConstructor
public class MemberContributionController {

    private final MemberContributionService memberContributionService;

//  Tạo contribution recordedByUserId thường lấy từ JWT
    @PostMapping("/{recordedByUserId}")
    @PreAuthorize("hasAnyRole('MENTOR','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<MemberContributionResponse> create(
            @PathVariable String recordedByUserId,
            @Valid @RequestBody MemberContributionDTO dto) {

        MemberContribution mc =
                memberContributionService.createContribution(dto, recordedByUserId);

        return ApiResponse.success(
                MemberContributionResponse.fromEntity(mc),
                "Tạo contribution thành công",
                HttpStatus.CREATED);
    }


//  Lấy tất cả contribution
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('MENTOR','TALENT','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<List<MemberContributionResponse>> getAll() {
        return ApiResponse.success(
                memberContributionService.getAll()
                        .stream()
                        .map(MemberContributionResponse::fromEntity)
                        .toList(),
                "Thành công",
                HttpStatus.OK);
    }

//    Lấy theo ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MENTOR','TALENT','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<MemberContributionResponse> getById(@PathVariable String id) {
        return ApiResponse.success(
                MemberContributionResponse.fromEntity(
                        memberContributionService.getById(id)),
                "Thành công",
                HttpStatus.OK);
    }


//  Lấy theo project

    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAnyRole('MENTOR','TALENT','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<List<MemberContributionResponse>> getByProject(
            @PathVariable String projectId) {

        return ApiResponse.success(
                memberContributionService.getByProject(projectId)
                        .stream()
                        .map(MemberContributionResponse::fromEntity)
                        .toList(),
                "Thành công",
                HttpStatus.OK);
    }


//     Lấy theo talent

    @GetMapping("/talent/{talentId}")
    @PreAuthorize("hasAnyRole('MENTOR','TALENT','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<List<MemberContributionResponse>> getByTalent(
            @PathVariable String talentId) {

        return ApiResponse.success(
                memberContributionService.getByTalent(talentId)
                        .stream()
                        .map(MemberContributionResponse::fromEntity)
                        .toList(),
                "Thành công",
                HttpStatus.OK);
    }


//  Lấy theo người ghi nhận

    @GetMapping("/recorder/{userId}")
    @PreAuthorize("hasAnyRole('MENTOR','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<List<MemberContributionResponse>> getByRecorder(
            @PathVariable String userId) {

        return ApiResponse.success(
                memberContributionService.getByRecorder(userId)
                        .stream()
                        .map(MemberContributionResponse::fromEntity)
                        .toList(),
                "Thành công",
                HttpStatus.OK);
    }
    @GetMapping("/type/{type}")
    @PreAuthorize("hasAnyRole('MENTOR','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<List<MemberContributionResponse>> byType(
            @PathVariable MemberContribution.ContributionType type) {
        List<MemberContribution> list = memberContributionService.getByType(type);
        return ApiResponse.success(
                list.stream().map(MemberContributionResponse::fromEntity).toList(),
                "OK",
                HttpStatus.OK);
    }

//   Cập nhật contribution
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MENTOR','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<MemberContributionResponse> update(
            @PathVariable String id,
            @RequestBody MemberContributionDTO dto) {

        MemberContribution mc =
                memberContributionService.updateContribution(id, dto);

        return ApiResponse.success(
                MemberContributionResponse.fromEntity(mc),
                "Cập nhật thành công",
                HttpStatus.OK);
    }


//   Xóa contribution
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MENTOR','LAB_ADMIN','SYSTEM_ADMIN')")
    public ApiResponse<String> delete(@PathVariable String id) {
        memberContributionService.deleteContribution(id);
        return ApiResponse.success("Xóa thành công", "OK", HttpStatus.OK);
    }
}