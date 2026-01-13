package com.example.labOdc.Service;

import java.util.List;

import com.example.labOdc.DTO.CompanyDTO;
import com.example.labOdc.Model.Company;

public interface CompanyService {
    Company createCompany(CompanyDTO companyDTO);

    List<Company> getAllCompanies();

    void deleteCompany(String id);

    Company getCompanyById(String id);

    Company updateCompany(CompanyDTO companyDTO, String id);
}
