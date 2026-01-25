package com.example.labOdc.Service;

import java.util.List;

import com.example.labOdc.DTO.CompanyDTO;
import com.example.labOdc.DTO.Response.CompanyResponse;

public interface CompanyService {
    CompanyResponse createCompany(CompanyDTO companyDTO);

    List<CompanyResponse> getAllCompanies();

    void deleteCompany(String id);

    CompanyResponse getCompanyById(String id);

    CompanyResponse updateCompany(CompanyDTO companyDTO, String id);

    // Các chức năng mới cho company
    CompanyResponse approveCompany(String id); // Phê duyệt hồ sơ công ty

    CompanyResponse rejectCompany(String id, String reason); // Từ chối hồ sơ công ty với lý do

}
