package com.example.labOdc.Repository;

import com.example.labOdc.Model.MentorPayment;
import com.example.labOdc.Model.MentorPaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface MentorPaymentRepository extends JpaRepository<MentorPayment, String> {

    List<MentorPayment> findByMentor_Id(String mentorId);

    List<MentorPayment> findByProject_Id(String projectId);

    List<MentorPayment> findByStatus(MentorPaymentStatus status);

    Optional<MentorPayment> findByFundAllocation_Id(String fundAllocationId);

    BigDecimal sumAmountByMentor_IdAndStatus(String mentorId, MentorPaymentStatus status);

    BigDecimal getTotalPaidAmountByMentor(String mentorId);
}