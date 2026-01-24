package com.example.labOdc.Service.Implement;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.LabAdminDTO;
import com.example.labOdc.DTO.Response.LabAdminResponse;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.LabAdmin;
import com.example.labOdc.Model.User;
import com.example.labOdc.Repository.LabAdminRepository;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.LabAdminService;
import org.springframework.security.core.Authentication;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LabAdminServiceImpl implements LabAdminService {

    private static final Logger logger = LoggerFactory.getLogger(LabAdminServiceImpl.class);
    private final LabAdminRepository labAdminRepository;
    private final UserRepository userRepository;

    /**
     * Chức năng: Tạo hồ sơ Lab Admin mới.
     * Repository: LabAdminRepository.save() - Lưu entity vào database.
     */
    @Override
    @Transactional
    public LabAdminResponse createLabAdmin(LabAdminDTO dto) {

        // Lấy SYSTEM_ADMIN đang đăng nhập
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Tạo LabAdmin
        LabAdmin labAdmin = LabAdmin.builder()
                .user(user)                
                .department(dto.getDepartment())
                .position(dto.getPosition())
                .build();

        LabAdmin saved = labAdminRepository.save(labAdmin);

        return LabAdminResponse.fromLabAdmin(saved);
    }

    @Override
    public List<LabAdminResponse> getAllLabAdmins() {
        return labAdminRepository.findAll().stream()
                .map(LabAdminResponse::fromLabAdmin)
                .toList();
    }

    /**
     * Chức năng: Xóa Lab Admin theo ID.
     * Repository: LabAdminRepository.findById() và delete() - Tìm và xóa entity.
     */
    @Override
    @Transactional
    public void deleteLabAdmin(String id) {
        logger.info("Deleting lab admin with ID: {}", id);
        LabAdmin labAdmin = labAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LabAdmin not found"));
        labAdminRepository.delete(labAdmin);
        logger.info("Lab admin deleted successfully");
    }

    /**
     * Chức năng: Lấy Lab Admin theo ID.
     * Repository: LabAdminRepository.findById() - Truy vấn entity theo ID.
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public LabAdminResponse getLabAdminById(String id) {
        logger.debug("Fetching lab admin with ID: {}", id);
        LabAdmin labAdmin = labAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LabAdmin not found"));
        return LabAdminResponse.fromLabAdmin(labAdmin);
    }

    /**
     * Chức năng: Cập nhật Lab Admin theo ID.
     * Repository: LabAdminRepository.findById() và save() - Tìm và cập nhật entity.
     */
    @Override
    @Transactional
    public LabAdminResponse updateLabAdmin(LabAdminDTO dto, String id) {
        logger.info("Updating lab admin with ID: {}", id);
        LabAdmin labAdmin = labAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LabAdmin not found"));

        // Update only non-null fields
        updateLabAdminFields(labAdmin, dto);

        LabAdmin updatedLabAdmin = labAdminRepository.save(labAdmin);
        logger.info("Lab admin updated successfully");
        return LabAdminResponse.fromLabAdmin(updatedLabAdmin);
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
