package com.yum.yumyums.dto.user;

import com.yum.yumyums.dto.StationDTO;
import com.yum.yumyums.entity.Station;
import com.yum.yumyums.entity.user.MarkStation;
import com.yum.yumyums.entity.user.Member;
import jakarta.persistence.EntityManager;
import lombok.Data;

@Data
public class MarkStationDTO {

    private int markStationId;
    private String memberId;
    private String stationId;
    private String memo;


    public static MarkStation dtoToEntity(MarkStationDTO markStationDTO, EntityManager entityManager){
        MarkStation markStation = new MarkStation();

        Member member = entityManager.find(Member.class, markStationDTO.getMemberId());
        Station station = entityManager.find(Station.class, markStationDTO.getStationId());

        markStation.setMember(member);
        markStation.setStation(station);
        markStation.setMemo(markStationDTO.getMemo());
        return markStation;
    }

}
