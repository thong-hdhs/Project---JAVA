package com.example.labOdc.Service;

import com.example.labOdc.DTO.MentorPaymentDTO;
import com.example.labOdc.DTO.Response.MentorPaymentResponse;
import com.example.labOdc.Model.MentorPaymentStatus;

import java.math.BigDecimal;
import java.util.List;

public interface MentorPaymentService {

    MentorPaymentResponse createFromAllocation(MentorPaymentDTO dto);

    MentorPaymentResponse updateStatus(String paymentId, MentorPaymentStatus newStatus,
                                       String approvedById, String notes);

    MentorPaymentResponse getById(String id);

    List<MentorPaymentResponse> getByMentorId(String mentorId);

    List<MentorPaymentResponse> getByProjectId(String projectId);

    BigDecimal getTotalPaidForMentor(String mentorId);//Mentor mentor
}