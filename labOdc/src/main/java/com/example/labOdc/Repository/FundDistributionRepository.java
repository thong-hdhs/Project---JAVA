package com.example.labOdc.Repository;

import com.example.labOdc.Model.FundDistribution;
import com.example.labOdc.Model.FundDistributionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FundDistributionRepository extends JpaRepository<FundDistribution, String> {    

    List<FundDistribution> findByFundAllocationId(String fundAllocationId);

    List<FundDistribution> findByTalentId(String talentId);

    List<FundDistribution> findByStatus(FundDistributionStatus status);
}