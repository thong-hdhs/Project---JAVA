package com.example.labOdc.Service;

import java.math.BigDecimal;
import java.util.List;

import com.example.labOdc.DTO.Response.TalentResponse;
import com.example.labOdc.DTO.TalentDTO;
import com.example.labOdc.Model.Talent;

public interface TalentService {
    TalentResponse createTalent(TalentDTO talentDTO);

    List<TalentResponse> getAllTalents();

    void deleteTalent(String id);

    TalentResponse getTalentById(String id);

    TalentResponse updateTalent(TalentDTO talentDTO, String id);

    List<TalentResponse> findByMajor(String major);

    List<TalentResponse> findByStatus(Talent.Status status);

}
