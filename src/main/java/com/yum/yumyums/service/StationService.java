package com.yum.yumyums.service;

import com.yum.yumyums.dto.StationDTO;
import com.yum.yumyums.entity.Station;
import reactor.core.publisher.Flux;

import java.util.List;

public interface StationService {
    List<Station> getAndSaveStations();
}
