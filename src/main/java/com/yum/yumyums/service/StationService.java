package com.yum.yumyums.service;

import com.yum.yumyums.dto.StationDTO;
import com.yum.yumyums.entity.Station;

import java.util.List;

public interface StationService {
    List<Station> getAndSaveStations();

    List<StationDTO> searchStations(String keyword);
}
