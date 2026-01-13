package com.example.labOdc.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.CompanyDTO;
import com.example.labOdc.DTO.Response.CompanyResponse;
import com.example.labOdc.Model.Company;
import com.example.labOdc.Service.CompanyService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/companies")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping("/")
    public ApiResponse<CompanyResponse> createCompany(@Valid @RequestBody CompanyDTO companyDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ApiResponse.error(errorMessages);
        }
        Company c = companyService.createCompany(companyDTO);
        return ApiResponse.success(CompanyResponse.fromCompany(c), "Created", HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ApiResponse<List<CompanyResponse>> getAllCompanies() {
        List<Company> list = companyService.getAllCompanies();
        return ApiResponse.success(list.stream().map(CompanyResponse::fromCompany).toList(), "OK", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable String id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/{id}")
    public ApiResponse<CompanyResponse> getCompanyById(@PathVariable String id) {
        Company c = companyService.getCompanyById(id);
        return ApiResponse.success(CompanyResponse.fromCompany(c), "OK", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ApiResponse<CompanyResponse> updateCompany(@Valid @RequestBody CompanyDTO companyDTO, @PathVariable String id) {
        Company c = companyService.updateCompany(companyDTO, id);
        return ApiResponse.success(CompanyResponse.fromCompany(c), "Updated", HttpStatus.OK);
    }
}
