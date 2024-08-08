package com.yum.yumyums.dto.chat;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.entity.chat.Party;
import com.yum.yumyums.entity.chat.PartyMember;
import com.yum.yumyums.enums.PayType;
import com.yum.yumyums.enums.RandomType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PartyDTO {

    private String id = null;
    private StoreDTO storeDTO = null;
    private PayType payType = null;
    private RandomType randomType;
    private List<PartyMemberDTO> partyMemberDTOs = new ArrayList<>();
    private LocalDateTime createTime = LocalDateTime.now();
    private int maxMemberCount;

    private boolean isActive = true; // 활성화된 파티
    private boolean isMatching = false; // 랜덤매칭인 파티



    public Party dtoToEntity() {
        Party party = Party.createParty(id, storeDTO.dtoToEntity(), payType, maxMemberCount);
        party.setRandomType(this.randomType);
        party.setActive(this.isActive);
        party.setCreateTime(this.createTime);
        party.setActive(isActive);
        party.setMatching(isMatching);

        if(partyMemberDTOs.size() > 1) {
            // 파티멤버 저장
            List<PartyMember> partyMembers = new ArrayList<>();
            for (PartyMemberDTO partyMemberDTO : partyMemberDTOs) {
                PartyMember partyMember = partyMemberDTO.dtoToEntity();
                partyMembers.add(partyMember);
            }
        }

        return party;
    }

    public static PartyDTO createPartyDtoFromMatchRequest(StoreDTO storeDTO, MatchRequestDTO matchRequestDTO) {
        PartyDTO partyDTO = new PartyDTO();
        partyDTO.setStoreDTO(storeDTO);
        partyDTO.setPayType(matchRequestDTO.getPayType());
        partyDTO.setMaxMemberCount(matchRequestDTO.getMaxMemberCount());
        partyDTO.setMatching(true);
        partyDTO.setCreateTime(LocalDateTime.now());

        return partyDTO;
    }

    //== 연관관계 메소드 ==//
    public void addPartyMember(PartyMemberDTO partyMemberDTO) {
        partyMemberDTOs.add(partyMemberDTO);
        partyMemberDTO.setPartyDTO(this);
    }

    public void setPartyMembersByPartyMemberList(List<PartyMember> partyMembers) {
        for (PartyMember partyMember : partyMembers) {
            partyMemberDTOs.add(partyMember.entityToDto());
        }
    }

    //== 조회 로직 ==//
    @JsonGetter("partyMemberCount")
    public int getPartyMemberCount() {
        return partyMemberDTOs.size();
    }

    @JsonGetter("payTypeKorName")
    public String getPayTypeKorName() {
        return payType.getKorName();
    }

    @JsonGetter("randomTypeKorName")
    public String getRandomTypeKorName() {
        if(randomType == null) {
            return null;
        }
        return randomType.getKorName();
    }
}
