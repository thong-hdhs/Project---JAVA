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
import com.example.labOdc.DTO.Response.TalentResponse;
import com.example.labOdc.DTO.TalentDTO;
import com.example.labOdc.Model.Talent;
import com.example.labOdc.Service.TalentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/talents")
public class TalentController {
    private final TalentService talentService;

    @PostMapping("/")
    public ApiResponse<TalentResponse> createTalent(@Valid @RequestBody TalentDTO talentDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ApiResponse.error(errorMessages);
        }
        Talent t = talentService.createTalent(talentDTO);
        return ApiResponse.success(TalentResponse.fromTalent(t), "Created", HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ApiResponse<List<TalentResponse>> getAllTalents() {
        List<Talent> list = talentService.getAllTalents();
        return ApiResponse.success(list.stream().map(TalentResponse::fromTalent).toList(), "OK", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTalent(@PathVariable String id) {
        talentService.deleteTalent(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/{id}")
    public ApiResponse<TalentResponse> getTalentById(@PathVariable String id) {
        Talent t = talentService.getTalentById(id);
        return ApiResponse.success(TalentResponse.fromTalent(t), "OK", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ApiResponse<TalentResponse> updateTalent(@Valid @RequestBody TalentDTO talentDTO, @PathVariable String id) {
        Talent t = talentService.updateTalent(talentDTO, id);
        return ApiResponse.success(TalentResponse.fromTalent(t), "Updated", HttpStatus.OK);
    }

    @GetMapping("/major/{major}")
    public ApiResponse<List<TalentResponse>> getTalentsByMajor(@PathVariable String major) {
        List<Talent> list = talentService.findByMajor(major);
        return ApiResponse.success(list.stream().map(TalentResponse::fromTalent).toList(), "OK", HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ApiResponse<List<TalentResponse>> getTalentsByStatus(@PathVariable Talent.Status status) {
        List<Talent> list = talentService.findByStatus(status);
        return ApiResponse.success(list.stream().map(TalentResponse::fromTalent).toList(), "OK", HttpStatus.OK);
    }
}
