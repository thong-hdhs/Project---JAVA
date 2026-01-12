package com.example.labOdc.Service.Implement;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.CompanyDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.Company;
import com.example.labOdc.Model.User;
import com.example.labOdc.Repository.CompanyRepository;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.CompanyService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Company createCompany(CompanyDTO companyDTO) {
        logger.info("Creating company: {}", companyDTO.getCompanyName());
        
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
                .status(companyDTO.getStatus())
                .rejectionReason(companyDTO.getRejectionReason())
                .build();

        if (companyDTO.getApprovedById() != null) {
            User approver = userRepository.findById(companyDTO.getApprovedById())
                    .orElseThrow(() -> new ResourceNotFoundException("Approver not found"));
            company.setApprovedBy(approver);
            company.setApprovedAt(companyDTO.getApprovedAt());
        }

        Company savedCompany = companyRepository.save(company);
        logger.info("Company created successfully with ID: {}", savedCompany.getId());
        return savedCompany;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Company> getAllCompanies() {
        logger.debug("Fetching all companies");
        return companyRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteCompany(String id) {
        logger.info("Deleting company with ID: {}", id);
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        companyRepository.delete(company);
        logger.info("Company deleted successfully");
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Company getCompanyById(String id) {
        logger.debug("Fetching company with ID: {}", id);
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
    }

    @Override
    @Transactional
    public Company updateCompany(CompanyDTO companyDTO, String id) {
        logger.info("Updating company with ID: {}", id);
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        // Update only non-null fields
        updateCompanyFields(company, companyDTO);

        if (companyDTO.getApprovedById() != null) {
            User approver = userRepository.findById(companyDTO.getApprovedById())
                    .orElseThrow(() -> new ResourceNotFoundException("Approver not found"));
            company.setApprovedBy(approver);
            company.setApprovedAt(companyDTO.getApprovedAt());
        }

        Company updatedCompany = companyRepository.save(company);
        logger.info("Company updated successfully");
        return updatedCompany;
    }

    /**
     * Helper method to update company fields from DTO
     * Only updates non-null fields to support partial updates
     */
    private void updateCompanyFields(Company company, CompanyDTO dto) {
        if (dto.getCompanyName() != null)
            company.setCompanyName(dto.getCompanyName());
        if (dto.getTaxCode() != null)
            company.setTaxCode(dto.getTaxCode());
        if (dto.getAddress() != null)
            company.setAddress(dto.getAddress());
        if (dto.getIndustry() != null)
            company.setIndustry(dto.getIndustry());
        if (dto.getDescription() != null)
            company.setDescription(dto.getDescription());
        if (dto.getWebsite() != null)
            company.setWebsite(dto.getWebsite());
        if (dto.getCompanySize() != null)
            company.setCompanySize(dto.getCompanySize());
        if (dto.getStatus() != null)
            company.setStatus(dto.getStatus());
        if (dto.getRejectionReason() != null)
            company.setRejectionReason(dto.getRejectionReason());
    }
}
