package com.yum.yumyums.entity.user;

import com.yum.yumyums.dto.user.MarkStationDTO;
import com.yum.yumyums.entity.Station;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MarkStation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @Column(length = 50)
    private String memo;

    public static MarkStationDTO entityToDto(MarkStation markStation){
        MarkStationDTO markStationDTO = new MarkStationDTO();

        markStationDTO.setMarkStationId(markStation.getId());
        markStationDTO.setMemberId(markStation.getMember().getId());
        markStationDTO.setStationId(markStation.getStation().getId());
        markStationDTO.setMemo(markStation.getMemo());
        return markStationDTO;
    }

}
