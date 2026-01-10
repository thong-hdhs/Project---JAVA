package com.example.labOdc.Service.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.LabAdminDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.LabAdmin;
import com.example.labOdc.Model.User;
import com.example.labOdc.Repository.LabAdminRepository;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.LabAdminService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LabAdminServiceImpl implements LabAdminService {

    private final LabAdminRepository labAdminRepository;
    private final UserRepository userRepository;

    @Override
    public LabAdmin createLabAdmin(LabAdminDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        LabAdmin l = LabAdmin.builder()
                .user(user)
                .department(dto.getDepartment())
                .position(dto.getPosition())
                .build();

        labAdminRepository.save(l);
        return l;
    }

    @Override
    public List<LabAdmin> getAllLabAdmins() {
        return labAdminRepository.findAll();
    }

    @Override
    public void deleteLabAdmin(String id) {
        labAdminRepository.deleteById(id);
    }

    @Override
    public LabAdmin getLabAdminById(String id) {
        return labAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LabAdmin not found"));
    }

    @Override
    public LabAdmin updateLabAdmin(LabAdminDTO dto, String id) {
        LabAdmin l = labAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LabAdmin not found"));

        if (dto.getDepartment() != null) l.setDepartment(dto.getDepartment());
        if (dto.getPosition() != null) l.setPosition(dto.getPosition());

        labAdminRepository.save(l);
        return l;
    }
}
