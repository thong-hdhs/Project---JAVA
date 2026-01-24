package com.example.labOdc.Service.Implement;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.Response.TalentResponse;
import com.example.labOdc.DTO.TalentDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Talent;
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

    /**
     * Chức năng: Tạo hồ sơ sinh viên mới.
     * Repository: TalentRepository.save() - Lưu entity vào database.
     */
    @Override
    @Transactional
    public TalentResponse createTalent(TalentDTO talentDTO) {
        logger.info("Creating talent");

        Talent talent = Talent.builder()
                .studentCode(talentDTO.getStudentCode())
                .major(talentDTO.getMajor())
                .year(talentDTO.getYear())
                .skills(talentDTO.getSkills())
                .certifications(talentDTO.getCertifications())
                .portfolioUrl(talentDTO.getPortfolioUrl())
                .githubUrl(talentDTO.getGithubUrl())
                .linkedinUrl(talentDTO.getLinkedinUrl())
                .status(Talent.Status.AVAILABLE)
                .build();

        Talent savedTalent = talentRepository.save(talent);
        logger.info("Talent created successfully with ID: {}", savedTalent.getId());
        return TalentResponse.fromTalent(savedTalent);
    }

    /**
     * Chức năng: Lấy danh sách tất cả sinh viên.
     * Repository: TalentRepository.findAll() - Truy vấn tất cả entities.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<TalentResponse> getAllTalents() {
        logger.debug("Fetching all talents");
        return talentRepository.findAll().stream()
                .map(TalentResponse::fromTalent)
                .toList();
    }

    /**
     * Chức năng: Xóa sinh viên theo ID.
     * Repository: TalentRepository.findById() và delete() - Tìm và xóa entity.
     */
    @Override
    @Transactional
    public void deleteTalent(String id) {
        logger.info("Deleting talent with ID: {}", id);
        Talent talent = talentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));
        talentRepository.delete(talent);
        logger.info("Talent deleted successfully");
    }

    /**
     * Chức năng: Lấy sinh viên theo ID.
     * Repository: TalentRepository.findById() - Truy vấn entity theo ID.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public TalentResponse getTalentById(String id) {
        logger.debug("Fetching talent with ID: {}", id);
        Talent talent = talentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));
        return TalentResponse.fromTalent(talent);
    }

    /**
     * Chức năng: Cập nhật sinh viên theo ID.
     * Repository: TalentRepository.findById() và save() - Tìm và cập nhật entity.
     */
    @Override
    @Transactional
    public TalentResponse updateTalent(TalentDTO talentDTO, String id) {
        logger.info("Updating talent with ID: {}", id);
        Talent talent = talentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));

        // Update only non-null fields
        updateTalentFields(talent, talentDTO);

        Talent updatedTalent = talentRepository.save(talent);
        logger.info("Talent updated successfully");
        return TalentResponse.fromTalent(updatedTalent);
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
    }

    /**
     * Chức năng: Lọc danh sách sinh viên theo ngành học.
     * Repository: TalentRepository.findByMajor() - Truy vấn theo major.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<TalentResponse> findByMajor(String major) {
        logger.debug("Finding talents by major: {}", major);
        return talentRepository.findByMajor(major).stream()
                .map(TalentResponse::fromTalent)
                .toList();
    }

    /**
     * Chức năng: Lọc danh sách sinh viên theo trạng thái.
     * Repository: TalentRepository.findByStatus() - Truy vấn theo status.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<TalentResponse> findByStatus(Talent.Status status) {
        logger.debug("Finding talents by status: {}", status);
        return talentRepository.findByStatus(status).stream()
                .map(TalentResponse::fromTalent)
                .toList();
    }

}
