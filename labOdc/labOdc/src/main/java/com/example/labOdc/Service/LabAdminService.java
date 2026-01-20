package com.example.labOdc.Service;

import java.util.List;

import com.example.labOdc.DTO.LabAdminDTO;
import com.example.labOdc.Model.LabAdmin;

public interface LabAdminService {
    LabAdmin createLabAdmin(LabAdminDTO dto);

    List<LabAdmin> getAllLabAdmins();

    void deleteLabAdmin(String id);

    LabAdmin getLabAdminById(String id);

    LabAdmin updateLabAdmin(LabAdminDTO dto, String id);
}
