package com.example.labOdc.Service.Implement;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.LabAdminDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.LabAdmin;
import com.example.labOdc.Model.User;
import com.example.labOdc.Repository.LabAdminRepository;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.LabAdminService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LabAdminServiceImpl implements LabAdminService {

    private static final Logger logger = LoggerFactory.getLogger(LabAdminServiceImpl.class);
    private final LabAdminRepository labAdminRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public LabAdmin createLabAdmin(LabAdminDTO dto) {
        logger.info("Creating lab admin with user ID: {}", dto.getUserId());
        
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        LabAdmin labAdmin = LabAdmin.builder()
                .user(user)
                .department(dto.getDepartment())
                .position(dto.getPosition())
                .build();

        LabAdmin savedLabAdmin = labAdminRepository.save(labAdmin);
        logger.info("Lab admin created successfully with ID: {}", savedLabAdmin.getId());
        return savedLabAdmin;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<LabAdmin> getAllLabAdmins() {
        logger.debug("Fetching all lab admins");
        return labAdminRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteLabAdmin(String id) {
        logger.info("Deleting lab admin with ID: {}", id);
        LabAdmin labAdmin = labAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LabAdmin not found"));
        labAdminRepository.delete(labAdmin);
        logger.info("Lab admin deleted successfully");
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public LabAdmin getLabAdminById(String id) {
        logger.debug("Fetching lab admin with ID: {}", id);
        return labAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LabAdmin not found"));
    }

    @Override
    @Transactional
    public LabAdmin updateLabAdmin(LabAdminDTO dto, String id) {
        logger.info("Updating lab admin with ID: {}", id);
        LabAdmin labAdmin = labAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LabAdmin not found"));

        // Update only non-null fields
        updateLabAdminFields(labAdmin, dto);

        LabAdmin updatedLabAdmin = labAdminRepository.save(labAdmin);
        logger.info("Lab admin updated successfully");
        return updatedLabAdmin;
    }

    /**
     * Helper method to update lab admin fields from DTO
     * Only updates non-null fields to support partial updates
     */
    private void updateLabAdminFields(LabAdmin labAdmin, LabAdminDTO dto) {
        if (dto.getDepartment() != null)
            labAdmin.setDepartment(dto.getDepartment());
        if (dto.getPosition() != null)
            labAdmin.setPosition(dto.getPosition());
    }
}
