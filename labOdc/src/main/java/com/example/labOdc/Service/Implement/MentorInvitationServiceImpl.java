//package com.example.labOdc.Service.Implement;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.example.labOdc.DTO.MentorInvitationDTO;
//import com.example.labOdc.Exception.ResourceNotFoundException;
//import com.example.labOdc.Model.MentorInvitation;
//import com.example.labOdc.Repository.MentorInvitationRepository;
//import com.example.labOdc.Service.MentorInvitationService;
//
//import lombok.AllArgsConstructor;
//
//@Service
//@AllArgsConstructor
//public class MentorInvitationServiceImpl implements MentorInvitationService {
//
//    private final MentorInvitationRepository mentorInvitationRepository;
//
//    @Override
//    public MentorInvitation createMentorInvitation(MentorInvitationDTO dto) {
//        if (mentorInvitationRepository.existsByProjectIdAndMentorId(dto.getProjectId(), dto.getMentorId())) {
//            throw new IllegalArgumentException("Invitation already exists for this project and mentor");
//        }
//
//        MentorInvitation mi = MentorInvitation.builder()
//                .projectId(dto.getProjectId())
//                .mentorId(dto.getMentorId())
//                .invitedBy(dto.getInvitedBy())
//                .invitationMessage(dto.getInvitationMessage())
//                .proposedFeePercentage(dto.getProposedFeePercentage())
//                .status(dto.getStatus()) // nếu null -> default PENDING
//                .respondedAt(dto.getRespondedAt())
//                .build();
//
//        mentorInvitationRepository.save(mi);
//        return mi;
//    }
//
//    @Override
//    public List<MentorInvitation> getAllMentorInvitation() {
//        return mentorInvitationRepository.findAll();
//    }
//
//    @Override
//    public MentorInvitation getMentorInvitationById(String id) {
//        return mentorInvitationRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("MentorInvitation", "id", id));
//    }
//
//    @Override
//    public MentorInvitation updateMentorInvitation(MentorInvitationDTO dto, String id) {
//        MentorInvitation mi = mentorInvitationRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("MentorInvitation", "id", id));
//
//        // Lưu ý: đổi projectId/mentorId có thể đụng unique (nếu bạn cần mình sẽ thêm
//        // check chặt)
//        if (dto.getProjectId() != null)
//            mi.setProjectId(dto.getProjectId());
//        if (dto.getMentorId() != null)
//            mi.setMentorId(dto.getMentorId());
//
//        if (dto.getInvitedBy() != null)
//            mi.setInvitedBy(dto.getInvitedBy());
//        if (dto.getInvitationMessage() != null)
//            mi.setInvitationMessage(dto.getInvitationMessage());
//        if (dto.getProposedFeePercentage() != null)
//            mi.setProposedFeePercentage(dto.getProposedFeePercentage());
//        if (dto.getStatus() != null)
//            mi.setStatus(dto.getStatus());
//        if (dto.getRespondedAt() != null)
//            mi.setRespondedAt(dto.getRespondedAt());
//
//        mentorInvitationRepository.save(mi);
//        return mi;
//    }
//
//    @Override
//    public void deleteMentorInvitation(String id) {
//        mentorInvitationRepository.deleteById(id);
//    }
//}