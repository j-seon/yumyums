package com.yum.yumyums.dto.chat;

import com.yum.yumyums.dto.FaqDTO;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.entity.chat.Party;
import com.yum.yumyums.entity.chat.PartyMember;
import com.yum.yumyums.enums.PayType;
import com.yum.yumyums.enums.RandomType;
import jakarta.mail.Part;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PartyDTO {

    private String id;
    private StoreDTO storeDTO;
    private PayType payType;
    private RandomType randomType;
    private boolean isActive;
    private List<PartyMemberDTO> partyMemberDTOs;

    public Party dtoToEntity() {
        Party party = new Party();
        party.setId(this.id);
        party.setStore(this.storeDTO.dtoToEntity());
        party.setPayType(this.payType);
        party.setRandomType(this.randomType);
        party.setActive(this.isActive);
        party.setPartyMembers(
                partyMemberDTOs.stream()
                .map(PartyMemberDTO::entityToDto)
                .collect(Collectors.toList())
        );

        return party;
    }

}
