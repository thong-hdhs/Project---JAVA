package com.example.labOdc.Service;

import java.util.List;

import com.example.labOdc.DTO.TalentDTO;
import com.example.labOdc.Model.Talent;

public interface TalentService {
    Talent createTalent(TalentDTO talentDTO);

    List<Talent> getAllTalents();

    void deleteTalent(String id);

    Talent getTalentById(String id);

    Talent updateTalent(TalentDTO talentDTO, String id);

    List<Talent> findByMajor(String major);

    List<Talent> findByStatus(Talent.Status status);
}
