package com.example.labOdc.Service.Implement;

import com.example.labOdc.DTO.LabFundAdvanceDTO;
import com.example.labOdc.DTO.Response.LabFundAdvanceResponse;
import com.example.labOdc.Model.*;
import com.example.labOdc.Repository.*;
import com.example.labOdc.Service.LabFundAdvanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LabFundAdvanceServiceImpl implements LabFundAdvanceService {

    private final LabFundAdvanceRepository labFundAdvanceRepository;
    private final ProjectRepository projectRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Override
    public LabFundAdvanceResponse createAdvance(LabFundAdvanceDTO dto) {

        Project project = null;
        if (dto.getProjectId() != null) {
                project = projectRepository.findById(dto.getProjectId())
                        .orElse(null);
        }

        Payment payment = null;
        if (dto.getPaymentId() != null) {
                payment = paymentRepository.findById(dto.getPaymentId())
                        .orElse(null);
        }

        LabFundAdvance advance = LabFundAdvance.builder()
                .project(project)
                .payment(payment)
                .advanceAmount(dto.getAdvanceAmount())
                .advanceReason(dto.getAdvanceReason())
                .status(LabFundAdvanceStatus.ADVANCED)
                .build();

        LabFundAdvance saved = labFundAdvanceRepository.save(advance);
        return LabFundAdvanceResponse.fromEntity(saved);
    }

    @Override
    public LabFundAdvanceResponse updateStatus(
            String advanceId,
            String statusStr,
            String approvedById) {

        LabFundAdvance advance = labFundAdvanceRepository.findById(advanceId)
                .orElseThrow(() -> new RuntimeException("Lab fund advance not found"));

        LabFundAdvanceStatus status =
                LabFundAdvanceStatus.valueOf(statusStr.toUpperCase());
        advance.setStatus(status);

        if (approvedById != null) {
            User approvedBy = userRepository.findById(approvedById)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            advance.setApprovedBy(approvedBy);
        }

        LabFundAdvance updated = labFundAdvanceRepository.save(advance);
        return LabFundAdvanceResponse.fromEntity(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public LabFundAdvanceResponse getById(String id) {
        return labFundAdvanceRepository.findById(id)
                .map(LabFundAdvanceResponse::fromEntity)
                .orElseThrow(() -> new RuntimeException("Lab fund advance not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LabFundAdvanceResponse> getByProjectId(String projectId) {
        return labFundAdvanceRepository.findByProjectId(projectId)
                .stream()
                .map(LabFundAdvanceResponse::fromEntity)
                .collect(Collectors.toList());
    }
}