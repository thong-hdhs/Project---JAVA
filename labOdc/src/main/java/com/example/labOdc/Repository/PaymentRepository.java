package com.example.labOdc.Repository;

import com.example.labOdc.Model.Payment;
import com.example.labOdc.Model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    List<Payment> findByProjectId(String projectId);

    List<Payment> findByCompanyId(String companyId);

    List<Payment> findByStatus(PaymentStatus status);
}