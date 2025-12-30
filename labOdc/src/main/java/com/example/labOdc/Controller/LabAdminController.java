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
import com.example.labOdc.DTO.LabAdminDTO;
import com.example.labOdc.DTO.Response.LabAdminResponse;
import com.example.labOdc.Model.LabAdmin;
import com.example.labOdc.Service.LabAdminService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/lab-admins")
public class LabAdminController {
    private final LabAdminService labAdminService;

    @PostMapping("/")
    public ApiResponse<LabAdminResponse> createLabAdmin(@Valid @RequestBody LabAdminDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ApiResponse.error(errorMessages);
        }
        LabAdmin l = labAdminService.createLabAdmin(dto);
        return ApiResponse.success(LabAdminResponse.fromLabAdmin(l), "Created", HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ApiResponse<List<LabAdminResponse>> getAll() {
        List<LabAdmin> list = labAdminService.getAllLabAdmins();
        return ApiResponse.success(list.stream().map(LabAdminResponse::fromLabAdmin).toList(), "OK", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ApiResponse<LabAdminResponse> getById(@PathVariable String id) {
        LabAdmin l = labAdminService.getLabAdminById(id);
        return ApiResponse.success(LabAdminResponse.fromLabAdmin(l), "OK", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ApiResponse<LabAdminResponse> update(@Valid @RequestBody LabAdminDTO dto, @PathVariable String id) {
        LabAdmin l = labAdminService.updateLabAdmin(dto, id);
        return ApiResponse.success(LabAdminResponse.fromLabAdmin(l), "Updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        labAdminService.deleteLabAdmin(id);
        return ResponseEntity.ok("Deleted");
    }
}
