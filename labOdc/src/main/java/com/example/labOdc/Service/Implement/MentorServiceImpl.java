package com.example.labOdc.Service.Implement;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.MentorDTO;
import com.example.labOdc.DTO.Response.MentorResponse;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Mentor;
import com.example.labOdc.Repository.MentorRepository;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.MentorService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;
    private final UserRepository userRepository;

    /**
     * Chức năng: Tạo hồ sơ Mentor mới.
     * Repository: MentorRepository.save() - Lưu entity vào database.
     */
    @Override
    public MentorResponse createMentor(MentorDTO mentorDTO) {
        // Note: userId not available in DTO
        Mentor mentor = Mentor.builder()
                .expertise(mentorDTO.getExpertise())
                .yearsExperience(mentorDTO.getYearsExperience())
                .bio(mentorDTO.getBio())
                .build();

        Mentor savedMentor = mentorRepository.save(mentor);
        return MentorResponse.fromMentor(savedMentor);
    }

    /**
     * Chức năng: Lấy danh sách tất cả Mentors.
     * Repository: MentorRepository.findAll() - Truy vấn tất cả entities.
     */
    @Override
    public List<MentorResponse> getAllMentors() {
        return mentorRepository.findAll().stream()
                .map(MentorResponse::fromMentor)
                .toList();
    }

    /**
     * Chức năng: Xóa Mentor theo ID.
     * Repository: MentorRepository.deleteById() - Xóa entity theo ID.
     */
    @Override
    public void deleteMentor(String id) {
        mentorRepository.deleteById(id);
    }

    /**
     * Chức năng: Lấy Mentor theo ID.
     * Repository: MentorRepository.findById() - Truy vấn entity theo ID.
     */
    @Override
    public MentorResponse getMentorById(String id) {
        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));
        return MentorResponse.fromMentor(mentor);
    }

    /**
     * Chức năng: Cập nhật Mentor theo ID.
     * Repository: MentorRepository.findById() và save() - Tìm và cập nhật entity.
     */
    @Override
    public MentorResponse updateMentor(MentorDTO mentorDTO, String id) {
        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));

        if (mentorDTO.getExpertise() != null)
            mentor.setExpertise(mentorDTO.getExpertise());
        if (mentorDTO.getYearsExperience() != null)
            mentor.setYearsExperience(mentorDTO.getYearsExperience());
        if (mentorDTO.getBio() != null)
            mentor.setBio(mentorDTO.getBio());

        Mentor updatedMentor = mentorRepository.save(mentor);
        return MentorResponse.fromMentor(updatedMentor);
    }

    /**
     * Chức năng: Lọc danh sách Mentors theo trạng thái.
     * Repository: MentorRepository.findByStatus() - Truy vấn theo status.
     */
    @Override
    public List<MentorResponse> findByStatus(Mentor.Status status) {
        return mentorRepository.findByStatus(status).stream()
                .map(MentorResponse::fromMentor)
                .toList();
    }

    /**
     * Chức năng: Lọc danh sách Mentors theo rating tối thiểu.
     * Repository: MentorRepository.findByRatingGreaterThanEqual() - Truy vấn theo rating.
     */
    @Override
    public List<MentorResponse> findByRatingGreaterThanEqual(BigDecimal rating) {
        return mentorRepository.findByRatingGreaterThanEqual(rating).stream()
                .map(MentorResponse::fromMentor)
                .toList();
    }

    /**
     * Chức năng: Chấp nhận lời mời làm mentor cho dự án.
     * Repository: Sử dụng ProjectInvitationRepository (placeholder) để cập nhật trạng thái.
     */
    @Override
    public void acceptInvite(String inviteId) {
        // Placeholder: Logic để chấp nhận lời mời
        // ProjectInvitation invite = projectInvitationRepository.findById(inviteId).orElseThrow();
        // invite.setStatus(ACCEPTED);
        // projectInvitationRepository.save(invite);
        // Cập nhật mentor status nếu cần
        System.out.println("Accepted invite: " + inviteId);
    }

    /**
     * Chức năng: Từ chối lời mời làm mentor cho dự án.
     * Repository: Sử dụng ProjectInvitationRepository để cập nhật trạng thái và lý do.
     */
    @Override
    public void rejectInvite(String inviteId, String reason) {
        // Placeholder: Logic từ chối lời mời
        // ProjectInvitation invite = projectInvitationRepository.findById(inviteId).orElseThrow();
        // invite.setStatus(REJECTED);
        // invite.setRejectionReason(reason);
        // projectInvitationRepository.save(invite);
        System.out.println("Rejected invite: " + inviteId + " with reason: " + reason);
    }

    

    

    

    
}
