//package com.example.labOdc.Service.Implement;
//
//import com.example.labOdc.DTO.MentorCandidateReviewDTO;
//import com.example.labOdc.Model.MentorCandidateReview;
//import com.example.labOdc.Repository.MentorCandidateReviewRepository;
//import com.example.labOdc.Service.MentorCandidateReviewService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//public class MentorCandidateReviewServiceImpl implements MentorCandidateReviewService {
//
//    @Autowired
//    private MentorCandidateReviewRepository repository;
//
//    private MentorCandidateReviewDTO toDto(MentorCandidateReview e) {
//        if (e == null) return null;
//        return MentorCandidateReviewDTO.builder()
//                .id(e.getId())
//                .mentorId(e.getMentorId())
//                .talentId(e.getTalentId())
//                .projectId(e.getProjectId())
//                .rating(e.getRating())
//                .comments(e.getComments())
//                .status(e.getStatus() != null ? e.getStatus().name() : null)
//                .reviewedById(e.getReviewedById())
//                .reviewedAt(e.getReviewedAt())
//                .build();
//    }
//
//    private MentorCandidateReview toEntity(MentorCandidateReviewDTO dto) {
//        if (dto == null) return null;
//        MentorCandidateReview e = MentorCandidateReview.builder()
//                .id(dto.getId())
//                .mentorId(dto.getMentorId())
//                .talentId(dto.getTalentId())
//                .projectId(dto.getProjectId())
//                .rating(dto.getRating())
//                .comments(dto.getComments())
//                .reviewedById(dto.getReviewedById())
//                .reviewedAt(dto.getReviewedAt())
//                .build();
//        if (dto.getStatus() != null) {
//            try {
//                e.setStatus(MentorCandidateReview.Status.valueOf(dto.getStatus()));
//            } catch (IllegalArgumentException ex) {
//                // ignore invalid status - leave null so prePersist sets default
//            }
//        }
//        return e;
//    }
//
//    @Override
//    public MentorCandidateReviewDTO create(MentorCandidateReviewDTO dto) {
//        MentorCandidateReview e = toEntity(dto);
//        MentorCandidateReview saved = repository.save(e);
//        return toDto(saved);
//    }
//
//    @Override
//    public MentorCandidateReviewDTO getById(String id) {
//        Optional<MentorCandidateReview> o = repository.findById(id);
//        return o.map(this::toDto).orElse(null);
//    }
//
//    @Override
//    public List<MentorCandidateReviewDTO> findByMentorId(String mentorId) {
//        return repository.findByMentorId(mentorId).stream().map(this::toDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<MentorCandidateReviewDTO> findByTalentId(String talentId) {
//        return repository.findByTalentId(talentId).stream().map(this::toDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<MentorCandidateReviewDTO> findByProjectId(String projectId) {
//        return repository.findByProjectId(projectId).stream().map(this::toDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public MentorCandidateReviewDTO update(String id, MentorCandidateReviewDTO dto) {
//        Optional<MentorCandidateReview> o = repository.findById(id);
//        if (!o.isPresent()) return null;
//        MentorCandidateReview e = o.get();
//        if (dto.getRating() != null) e.setRating(dto.getRating());
//        if (dto.getComments() != null) e.setComments(dto.getComments());
//        if (dto.getStatus() != null) {
//            try {
//                e.setStatus(MentorCandidateReview.Status.valueOf(dto.getStatus()));
//                if (e.getStatus() != MentorCandidateReview.Status.PENDING && e.getReviewedAt() == null) {
//                    e.setReviewedAt(java.time.LocalDateTime.now());
//                }
//            } catch (IllegalArgumentException ex) { }
//        }
//        if (dto.getReviewedById() != null) e.setReviewedById(dto.getReviewedById());
//        MentorCandidateReview saved = repository.save(e);
//        return toDto(saved);
//    }
//
//    @Override
//    public void delete(String id) {
//        repository.deleteById(id);
//    }
//}
