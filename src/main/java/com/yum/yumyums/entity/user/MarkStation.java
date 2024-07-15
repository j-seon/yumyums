package com.yum.yumyums.entity.user;

import com.yum.yumyums.dto.user.MarkStationDTO;
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
    private int markStationId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(length = 10, nullable = false)
    private String stationId;

    @Column(length = 50)
    private String memo;

    public static MarkStation toSaveEntity(MarkStationDTO markStationDTO){
        MarkStation markStation = new MarkStation();
        markStation.setMember(markStationDTO.getMember());
        markStation.setStationId(markStationDTO.getStationId());
        markStation.setMemo(markStationDTO.getMemo());
        return markStation;
    }
}
