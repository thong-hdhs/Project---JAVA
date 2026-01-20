//package com.example.labOdc.Service.Implement;
//
//import com.example.labOdc.DTO.LabFundAdvanceDTO;
//import com.example.labOdc.DTO.Response.LabFundAdvanceResponse;
//import com.example.labOdc.Model.*;
//import com.example.labOdc.Repository.*;
//import com.example.labOdc.Service.LabFundAdvanceService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class LabFundAdvanceServiceImpl implements LabFundAdvanceService {
//
//    private final LabFundAdvanceRepository labFundAdvanceRepository;
//    private final ProjectRepository projectRepository;
//    private final PaymentRepository paymentRepository;
//    private final UserRepository userRepository;
//
//    @Override
//    public LabFundAdvanceResponse createAdvance(LabFundAdvanceDTO dto) {
//        Project project = projectRepository.findById(dto.getProjectId())
//                .orElseThrow(() -> new RuntimeException("Project not found"));
//
//        Payment payment = null;
//        if (dto.getPaymentId() != null) {
//            payment = paymentRepository.findById(dto.getPaymentId())
//                    .orElseThrow(() -> new RuntimeException("Payment not found"));
//        }
//
//        LabFundAdvance advance = LabFundAdvance.builder()
//                .project(project)
//                .payment(payment)
//                .advanceAmount(dto.getAdvanceAmount())
//                .advanceReason(dto.getAdvanceReason())
//                .status(LabFundAdvanceStatus.ADVANCED)
//                .build();
//
//        LabFundAdvance saved = labFundAdvanceRepository.save(advance);
//        return mapToResponse(saved);
//    }
//
//    @Override
//    public LabFundAdvanceResponse updateStatus(String advanceId, String statusStr, String approvedById) {
//        LabFundAdvance advance = labFundAdvanceRepository.findById(advanceId)
//                .orElseThrow(() -> new RuntimeException("Lab fund advance not found"));
//
//        LabFundAdvanceStatus status = LabFundAdvanceStatus.valueOf(statusStr.toUpperCase());
//        advance.setStatus(status);
//
//        if (approvedById != null) {
//            User approvedBy = userRepository.findById(approvedById)
//                    .orElseThrow(() -> new RuntimeException("User not found"));
//            advance.setApprovedBy(approvedBy);
//        }
//
//        LabFundAdvance updated = labFundAdvanceRepository.save(advance);
//        return mapToResponse(updated);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public LabFundAdvanceResponse getById(String id) {
//        LabFundAdvance advance = labFundAdvanceRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Lab fund advance not found"));
//        return mapToResponse(advance);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<LabFundAdvanceResponse> getByProjectId(String projectId) {
//        return labFundAdvanceRepository.findByProjectId(projectId)
//                .stream()
//                .map(this::mapToResponse)
//                .collect(Collectors.toList());
//    }
//
//    private LabFundAdvanceResponse mapToResponse(LabFundAdvance advance) {
//        return LabFundAdvanceResponse.builder()
//                .id(advance.getId())
//                .projectName(advance.getProject().getProjectName())
//                .projectCode(advance.getProject().getProjectCode())
//                .paymentTransactionId(advance.getPayment() != null ? advance.getPayment().getTransactionId() : null)
//                .advanceAmount(advance.getAdvanceAmount())
//                .advanceReason(advance.getAdvanceReason())
//                .status(advance.getStatus())
//                .approvedByName(advance.getApprovedBy() != null ? advance.getApprovedBy().getFullName() : null)
//                .createdAt(advance.getCreatedAt())
//                .updatedAt(advance.getUpdatedAt())
//                .build();
//    }
//}