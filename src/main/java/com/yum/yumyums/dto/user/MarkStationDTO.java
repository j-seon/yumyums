package com.yum.yumyums.dto.user;

import com.yum.yumyums.entity.user.MarkStation;
import com.yum.yumyums.entity.user.Member;
import lombok.Data;

@Data
public class MarkStationDTO {

    private int markStationId;
    private Member member;
    private String stationId;
    private String memo;

    public static MarkStationDTO toMarkStationDTO(MarkStation markStation){
        MarkStationDTO markStationDTO = new MarkStationDTO();
        markStationDTO.setMarkStationId(markStation.getId());
        markStationDTO.setMember(markStation.getMember());
        markStationDTO.setStationId(markStation.getStationId());
        markStationDTO.setMemo(markStation.getMemo());
        return markStationDTO;
    }
}
