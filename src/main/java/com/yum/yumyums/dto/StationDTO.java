package com.yum.yumyums.dto;

import com.yum.yumyums.entity.Station;
import lombok.Data;

@Data
public class StationDTO {
    private String stationId;
    private String name;
    private String line;
    private String convx;
    private String convy;

    public static StationDTO entityToDto(Station station){
        StationDTO stationDTO = new StationDTO();
        stationDTO.setStationId(station.getId());
        stationDTO.setName(station.getName());
        stationDTO.setLine(station.getLine());
        stationDTO.setConvx(station.getConvx());
        stationDTO.setConvy(station.getConvy());
        return stationDTO;
    }
}
