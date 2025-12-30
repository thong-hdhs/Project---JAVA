package com.example.labOdc.Service.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.TalentDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Talent;
import com.example.labOdc.Model.User;
import com.example.labOdc.Repository.TalentRepository;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.TalentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TalentServiceImpl implements TalentService {

    private final TalentRepository talentRepository;
    private final UserRepository userRepository;

    @Override
    public Talent createTalent(TalentDTO talentDTO) {
        User user = userRepository.findById(talentDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Talent talent = Talent.builder()
                .user(user)
                .studentCode(talentDTO.getStudentCode())
                .major(talentDTO.getMajor())
                .year(talentDTO.getYear())
                .skills(talentDTO.getSkills())
                .certifications(talentDTO.getCertifications())
                .portfolioUrl(talentDTO.getPortfolioUrl())
                .githubUrl(talentDTO.getGithubUrl())
                .linkedinUrl(talentDTO.getLinkedinUrl())
                .gpa(talentDTO.getGpa())
                .status(talentDTO.getStatus() != null ? talentDTO.getStatus() : Talent.Status.AVAILABLE)
                .build();

        talentRepository.save(talent);
        return talent;
    }

    @Override
    public List<Talent> getAllTalents() {
        return talentRepository.findAll();
    }

    @Override
    public void deleteTalent(String id) {
        talentRepository.deleteById(id);
    }

    @Override
    public Talent getTalentById(String id) {
        return talentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));
    }

    @Override
    public Talent updateTalent(TalentDTO talentDTO, String id) {
        Talent talent = talentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));

        if (talentDTO.getStudentCode() != null)
            talent.setStudentCode(talentDTO.getStudentCode());
        if (talentDTO.getMajor() != null)
            talent.setMajor(talentDTO.getMajor());
        if (talentDTO.getYear() != null)
            talent.setYear(talentDTO.getYear());
        if (talentDTO.getSkills() != null)
            talent.setSkills(talentDTO.getSkills());
        if (talentDTO.getCertifications() != null)
            talent.setCertifications(talentDTO.getCertifications());
        if (talentDTO.getPortfolioUrl() != null)
            talent.setPortfolioUrl(talentDTO.getPortfolioUrl());
        if (talentDTO.getGithubUrl() != null)
            talent.setGithubUrl(talentDTO.getGithubUrl());
        if (talentDTO.getLinkedinUrl() != null)
            talent.setLinkedinUrl(talentDTO.getLinkedinUrl());
        if (talentDTO.getGpa() != null)
            talent.setGpa(talentDTO.getGpa());
        if (talentDTO.getStatus() != null)
            talent.setStatus(talentDTO.getStatus());

        talentRepository.save(talent);
        return talent;
    }

    @Override
    public List<Talent> findByMajor(String major) {
        return talentRepository.findByMajor(major);
    }

    @Override
    public List<Talent> findByStatus(Talent.Status status) {
        return talentRepository.findByStatus(status);
    }
}
