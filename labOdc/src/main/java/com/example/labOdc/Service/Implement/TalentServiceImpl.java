package com.example.labOdc.Service.Implement;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.TalentDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Talent;
import com.example.labOdc.Model.User;
import com.example.labOdc.Repository.TalentRepository;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.TalentService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TalentServiceImpl implements TalentService {

    private static final Logger logger = LoggerFactory.getLogger(TalentServiceImpl.class);
    private final TalentRepository talentRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Talent createTalent(TalentDTO talentDTO) {
        logger.info("Creating talent with user ID: {}", talentDTO.getUserId());
        
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

        Talent savedTalent = talentRepository.save(talent);
        logger.info("Talent created successfully with ID: {}", savedTalent.getId());
        return savedTalent;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Talent> getAllTalents() {
        logger.debug("Fetching all talents");
        return talentRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteTalent(String id) {
        logger.info("Deleting talent with ID: {}", id);
        Talent talent = talentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));
        talentRepository.delete(talent);
        logger.info("Talent deleted successfully");
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Talent getTalentById(String id) {
        logger.debug("Fetching talent with ID: {}", id);
        return talentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));
    }

    @Override
    @Transactional
    public Talent updateTalent(TalentDTO talentDTO, String id) {
        logger.info("Updating talent with ID: {}", id);
        Talent talent = talentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));

        // Update only non-null fields
        updateTalentFields(talent, talentDTO);

        Talent updatedTalent = talentRepository.save(talent);
        logger.info("Talent updated successfully");
        return updatedTalent;
    }

    /**
     * Helper method to update talent fields from DTO
     * Only updates non-null fields to support partial updates
     */
    private void updateTalentFields(Talent talent, TalentDTO dto) {
        if (dto.getStudentCode() != null)
            talent.setStudentCode(dto.getStudentCode());
        if (dto.getMajor() != null)
            talent.setMajor(dto.getMajor());
        if (dto.getYear() != null)
            talent.setYear(dto.getYear());
        if (dto.getSkills() != null)
            talent.setSkills(dto.getSkills());
        if (dto.getCertifications() != null)
            talent.setCertifications(dto.getCertifications());
        if (dto.getPortfolioUrl() != null)
            talent.setPortfolioUrl(dto.getPortfolioUrl());
        if (dto.getGithubUrl() != null)
            talent.setGithubUrl(dto.getGithubUrl());
        if (dto.getLinkedinUrl() != null)
            talent.setLinkedinUrl(dto.getLinkedinUrl());
        if (dto.getGpa() != null)
            talent.setGpa(dto.getGpa());
        if (dto.getStatus() != null)
            talent.setStatus(dto.getStatus());
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Talent> findByMajor(String major) {
        logger.debug("Finding talents by major: {}", major);
        return talentRepository.findByMajor(major);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Talent> findByStatus(Talent.Status status) {
        logger.debug("Finding talents by status: {}", status);
        return talentRepository.findByStatus(status);
    }
}
