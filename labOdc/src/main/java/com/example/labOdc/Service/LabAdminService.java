package com.example.labOdc.Service;

import java.util.List;

import com.example.labOdc.DTO.LabAdminDTO;
import com.example.labOdc.DTO.Response.LabAdminResponse;

public interface LabAdminService {
    LabAdminResponse createLabAdmin(LabAdminDTO dto);

    List<LabAdminResponse> getAllLabAdmins();

    void deleteLabAdmin(String id);

    LabAdminResponse getLabAdminById(String id);

    LabAdminResponse updateLabAdmin(LabAdminDTO dto, String id);


}
