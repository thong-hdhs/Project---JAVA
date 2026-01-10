package com.example.labOdc.Service.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.CompanyDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Company;
import com.example.labOdc.Model.User;
import com.example.labOdc.Repository.CompanyRepository;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.CompanyService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Override
    public Company createCompany(CompanyDTO companyDTO) {
        User user = userRepository.findById(companyDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Company company = Company.builder()
                .user(user)
                .companyName(companyDTO.getCompanyName())
                .taxCode(companyDTO.getTaxCode())
                .address(companyDTO.getAddress())
                .industry(companyDTO.getIndustry())
                .description(companyDTO.getDescription())
                .website(companyDTO.getWebsite())
                .companySize(companyDTO.getCompanySize())
                .status(companyDTO.getStatus() != null ? companyDTO.getStatus() : null)
                .rejectionReason(companyDTO.getRejectionReason())
                .build();

        if (companyDTO.getApprovedById() != null) {
            User approver = userRepository.findById(companyDTO.getApprovedById())
                    .orElseThrow(() -> new ResourceNotFoundException("Approver not found"));
            company.setApprovedBy(approver);
            company.setApprovedAt(companyDTO.getApprovedAt());
        }

        companyRepository.save(company);
        return company;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public void deleteCompany(String id) {
        companyRepository.deleteById(id);
    }

    @Override
    public Company getCompanyById(String id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
    }

    @Override
    public Company updateCompany(CompanyDTO companyDTO, String id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        if (companyDTO.getCompanyName() != null)
            company.setCompanyName(companyDTO.getCompanyName());
        if (companyDTO.getTaxCode() != null)
            company.setTaxCode(companyDTO.getTaxCode());
        if (companyDTO.getAddress() != null)
            company.setAddress(companyDTO.getAddress());
        if (companyDTO.getIndustry() != null)
            company.setIndustry(companyDTO.getIndustry());
        if (companyDTO.getDescription() != null)
            company.setDescription(companyDTO.getDescription());
        if (companyDTO.getWebsite() != null)
            company.setWebsite(companyDTO.getWebsite());
        if (companyDTO.getCompanySize() != null)
            company.setCompanySize(companyDTO.getCompanySize());
        if (companyDTO.getStatus() != null)
            company.setStatus(companyDTO.getStatus());
        if (companyDTO.getRejectionReason() != null)
            company.setRejectionReason(companyDTO.getRejectionReason());

        if (companyDTO.getApprovedById() != null) {
            User approver = userRepository.findById(companyDTO.getApprovedById())
                    .orElseThrow(() -> new ResourceNotFoundException("Approver not found"));
            company.setApprovedBy(approver);
            company.setApprovedAt(companyDTO.getApprovedAt());
        }

        companyRepository.save(company);
        return company;
    }
}
