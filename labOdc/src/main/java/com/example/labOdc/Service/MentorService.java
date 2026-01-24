package com.example.labOdc.Service;

import java.math.BigDecimal;
import java.util.List;

import com.example.labOdc.DTO.MentorDTO;
import com.example.labOdc.DTO.Response.MentorResponse;
import com.example.labOdc.Model.Mentor;

public interface MentorService {
    MentorResponse createMentor(MentorDTO mentorDTO);

    List<MentorResponse> getAllMentors();

    void deleteMentor(String id);

    MentorResponse getMentorById(String id);

    MentorResponse updateMentor(MentorDTO mentorDTO, String id);

    List<MentorResponse> findByStatus(Mentor.Status status);

    List<MentorResponse> findByRatingGreaterThanEqual(BigDecimal rating);

    /**
     * Chức năng: Chấp nhận lời mời làm mentor cho dự án.
     * Service: MentorService.acceptInvite() - Xử lý chấp nhận và cập nhật trạng thái.
     */
    void acceptInvite(String inviteId);

    /**
     * Chức năng: Từ chối lời mời làm mentor cho dự án.
     * Service: MentorService.rejectInvite() - Xử lý từ chối và ghi nhận lý do.
     */
    void rejectInvite(String inviteId, String reason);
   
}
