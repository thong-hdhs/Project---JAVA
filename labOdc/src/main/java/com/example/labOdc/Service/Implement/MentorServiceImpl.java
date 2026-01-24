package com.example.labOdc.Service.Implement;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.MentorDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Mentor;
import com.example.labOdc.Model.User;
import com.example.labOdc.Repository.MentorRepository;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.MentorService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;
    private final UserRepository userRepository;

    @Override
    public Mentor createMentor(MentorDTO mentorDTO) {
        if (mentorDTO.getUserId() == null || mentorDTO.getUserId().isBlank()) {
            throw new IllegalArgumentException("userId is required");
        }

        User user = userRepository.findById(mentorDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Mentor mentor = Mentor.builder()
                .user(user)
                .expertise(mentorDTO.getExpertise())
                .yearsExperience(mentorDTO.getYearsExperience())
                .bio(mentorDTO.getBio())
                .rating(mentorDTO.getRating() != null ? mentorDTO.getRating() : BigDecimal.ZERO)
                .totalProjects(mentorDTO.getTotalProjects() != null ? mentorDTO.getTotalProjects() : 0)
                .status(mentorDTO.getStatus() != null ? mentorDTO.getStatus() : Mentor.Status.AVAILABLE)
                .build();

        return mentorRepository.save(mentor);
    }

    @Override
    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
    }

    @Override
    public void deleteMentor(String id) {
        mentorRepository.deleteById(id);
    }

    @Override
    public Mentor getMentorById(String id) {
        return mentorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));
    }

    @Override
    public Mentor updateMentor(MentorDTO mentorDTO, String id) {
        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));

        if (mentorDTO.getExpertise() != null)
            mentor.setExpertise(mentorDTO.getExpertise());
        if (mentorDTO.getYearsExperience() != null)
            mentor.setYearsExperience(mentorDTO.getYearsExperience());
        if (mentorDTO.getBio() != null)
            mentor.setBio(mentorDTO.getBio());

        if (mentorDTO.getRating() != null)
            mentor.setRating(mentorDTO.getRating());
        if (mentorDTO.getTotalProjects() != null)
            mentor.setTotalProjects(mentorDTO.getTotalProjects());
        if (mentorDTO.getStatus() != null)
            mentor.setStatus(mentorDTO.getStatus());

        return mentorRepository.save(mentor);
    }

    @Override
    public List<Mentor> findByStatus(Mentor.Status status) {
        return mentorRepository.findByStatus(status);
    }

    @Override
    public List<Mentor> findByRatingGreaterThanEqual(BigDecimal rating) {
        return mentorRepository.findByRatingGreaterThanEqual(rating);
    }
}