package com.yum.yumyums.dto;

import com.yum.yumyums.entity.Station;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationDTO {
    private String outStnNum;
    private String stnKrNm;
    private String lineNm;
    private String convX;
    private String convY;

    public static StationDTO entityToDto(Station station){
        StationDTO stationDTO = new StationDTO();
        stationDTO.setOutStnNum(station.getId());
        stationDTO.setStnKrNm(station.getName());
        stationDTO.setLineNm(station.getLine());
        stationDTO.setConvX(String.valueOf(station.getConvX()));
        stationDTO.setConvY(String.valueOf(station.getConvY()));
        return stationDTO;
    }
}
