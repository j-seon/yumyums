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

    @Column(length = 100, nullable = false)
    private String convx;

    @Column(length = 100, nullable = false)
    private String convy;

    public static Station dtoToEntity(StationDTO stationDTO){
        Station station = new Station();

        station.setId(stationDTO.getStationId());
        station.setName(stationDTO.getName());
        station.setLine(stationDTO.getLine());
        station.setConvx(stationDTO.getConvx());
        station.setConvy(stationDTO.getConvy());
        return station;
    }
}
