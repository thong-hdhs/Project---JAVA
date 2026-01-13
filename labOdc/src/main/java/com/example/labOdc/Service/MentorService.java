package com.example.labOdc.Service;

import java.math.BigDecimal;
import java.util.List;

import com.example.labOdc.DTO.MentorDTO;
import com.example.labOdc.Model.Mentor;

public interface MentorService {
    Mentor createMentor(MentorDTO mentorDTO);

    List<Mentor> getAllMentors();

    void deleteMentor(String id);

    Mentor getMentorById(String id);

    Mentor updateMentor(MentorDTO mentorDTO, String id);

    List<Mentor> findByStatus(Mentor.Status status);

    List<Mentor> findByRatingGreaterThanEqual(BigDecimal rating);
}
