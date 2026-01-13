package com.example.labOdc.Controller;

import java.math.BigDecimal;
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
import com.example.labOdc.DTO.MentorDTO;
import com.example.labOdc.DTO.Response.MentorResponse;
import com.example.labOdc.Model.Mentor;
import com.example.labOdc.Service.MentorService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/mentors")
public class MentorController {
    private final MentorService mentorService;

    @PostMapping("/")
    public ApiResponse<MentorResponse> createMentor(@Valid @RequestBody MentorDTO mentorDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ApiResponse.error(errorMessages);
        }
        Mentor m = mentorService.createMentor(mentorDTO);
        return ApiResponse.success(MentorResponse.fromMentor(m), "Created", HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ApiResponse<List<MentorResponse>> getAllMentors() {
        List<Mentor> list = mentorService.getAllMentors();
        return ApiResponse.success(list.stream().map(MentorResponse::fromMentor).toList(), "OK", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMentor(@PathVariable String id) {
        mentorService.deleteMentor(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/{id}")
    public ApiResponse<MentorResponse> getMentorById(@PathVariable String id) {
        Mentor m = mentorService.getMentorById(id);
        return ApiResponse.success(MentorResponse.fromMentor(m), "OK", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ApiResponse<MentorResponse> updateMentor(@Valid @RequestBody MentorDTO mentorDTO, @PathVariable String id) {
        Mentor m = mentorService.updateMentor(mentorDTO, id);
        return ApiResponse.success(MentorResponse.fromMentor(m), "Updated", HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ApiResponse<List<MentorResponse>> getMentorsByStatus(@PathVariable Mentor.Status status) {
        List<Mentor> list = mentorService.findByStatus(status);
        return ApiResponse.success(list.stream().map(MentorResponse::fromMentor).toList(), "OK", HttpStatus.OK);
    }

    @GetMapping("/rating/{minRating}")
    public ApiResponse<List<MentorResponse>> getMentorsByMinRating(@PathVariable BigDecimal minRating) {
        List<Mentor> list = mentorService.findByRatingGreaterThanEqual(minRating);
        return ApiResponse.success(list.stream().map(MentorResponse::fromMentor).toList(), "OK", HttpStatus.OK);
    }
}
