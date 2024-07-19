package com.yum.yumyums.entity;

import com.yum.yumyums.dto.StationDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Station {
    @Id
    @Column(length = 10)
    private String id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String line;

    @Column(nullable = false)
    private double convX;

    @Column(nullable = false)
    private double convY;

    public static Station dtoToEntity(StationDTO stationDTO){
        Station station = new Station();

        station.setId(stationDTO.getOutStnNum());
        station.setName(stationDTO.getStnKrNm());
        station.setLine(stationDTO.getLineNm());
        station.setConvX(Double.parseDouble(stationDTO.getConvX()));
        station.setConvY(Double.parseDouble(stationDTO.getConvY()));
        return station;
    }
}
