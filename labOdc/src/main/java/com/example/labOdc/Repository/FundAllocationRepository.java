package com.example.labOdc.Repository;

import com.example.labOdc.Model.FundAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FundAllocationRepository extends JpaRepository<FundAllocation, String> {

    Optional<FundAllocation> findByPaymentId(String paymentId);
    

    // 1 Project có thể có N FundAllocation
    // Đã thiết kế:
    // - 1 project → nhiều payment
    // - mỗi payment → nhiều đợt phân bổ (FundAllocation)
    //
    // ⇒ Quan hệ là 1–N
    // ⇒ Optional<FundAllocation> chỉ phù hợp cho quan hệ 1–1
    // ⇒ Trường hợp này phải dùng List<FundAllocation>
    
    List<FundAllocation> findByProjectId(String projectId);
}