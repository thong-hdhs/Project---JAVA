package com.example.labOdc.Controller;


import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.MemberContributionDTO;
import com.example.labOdc.DTO.Response.MemberContributionResponse;
import com.example.labOdc.Service.MemberContributionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membercontributions")
@AllArgsConstructor
public class MemberContributionController {

    private final MemberContributionService service;

    @PostMapping
    public ApiResponse<MemberContributionResponse> create(
            @RequestBody MemberContributionDTO memberContributionDTO) {

        return ApiResponse.success(
                MemberContributionResponse.fromEntity(service.create(memberContributionDTO)),
                "Ghi nhận đóng góp thành công",
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ApiResponse<List<MemberContributionResponse>> getAll() {
        return ApiResponse.success(
                service.getAll().stream()
                        .map(MemberContributionResponse::fromEntity)
                        .toList(),
                "OK",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<MemberContributionResponse> getById(
            @PathVariable String id) {

        return ApiResponse.success(
                MemberContributionResponse.fromEntity(service.getById(id)),
                "OK",
                HttpStatus.OK
        );
    }

    @GetMapping("/project/{projectId}")
    public ApiResponse<List<MemberContributionResponse>> getByProject(
            @PathVariable String projectId) {

        return ApiResponse.success(
                service.getByProject(projectId).stream()
                        .map(MemberContributionResponse::fromEntity)
                        .toList(),
                "OK",
                HttpStatus.OK
        );
    }

    @GetMapping("/talent/{talentId}")
    public ApiResponse<List<MemberContributionResponse>> getByTalent(
            @PathVariable String talentId) {

        return ApiResponse.success(
                service.getByTalent(talentId).stream()
                        .map(MemberContributionResponse::fromEntity)
                        .toList(),
                "OK",
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<MemberContributionResponse> update(
            @PathVariable String id,
            @RequestBody MemberContributionDTO memberContributionDTO) {

        return ApiResponse.success(
                MemberContributionResponse.fromEntity(service.update(id, memberContributionDTO)),
                "Cập nhật đóng góp thành công",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.success(
                "Xóa thành công",
                "OK",
                HttpStatus.OK
        );
    }
}
