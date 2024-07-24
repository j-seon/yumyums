package com.yum.yumyums.dto.chat;

import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.entity.chat.Party;
import com.yum.yumyums.entity.chat.PartyMember;
import com.yum.yumyums.enums.PayType;
import com.yum.yumyums.enums.RandomType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PartyDTO {

    private String id = null;
    private StoreDTO storeDTO = null;
    private PayType payType = null;
    private RandomType randomType;
    private boolean isActive = true;
    private List<PartyMemberDTO> partyMemberDTOs = new ArrayList<>();



    public Party dtoToEntity() {
        Party party = Party.createParty(id, storeDTO.dtoToEntity(), payType);
        party.setRandomType(this.randomType);
        party.setActive(this.isActive);

        // 파티멤버 저장
        List<PartyMember> partyMembers = new ArrayList<>();
        for (PartyMemberDTO partyMemberDTO : partyMemberDTOs) {
            PartyMember partyMember = partyMemberDTO.dtoToEntity();
            partyMembers.add(partyMember);
        }
        return party;
    }

    // 연관관계 메소드
    public void addPartyMember(PartyMemberDTO partyMemberDTO) {
        partyMemberDTOs.add(partyMemberDTO);
        partyMemberDTO.setPartyDTO(this);
    }

    public void setPartyMembersByPartyMemberList(List<PartyMember> partyMembers) {
        for (PartyMember partyMember : partyMembers) {
            partyMemberDTOs.add(partyMember.entityToDto());
        }
    }

    // 조회 로직
    public int getPartyMemberCount() {
        return partyMemberDTOs.size();
    }
}
