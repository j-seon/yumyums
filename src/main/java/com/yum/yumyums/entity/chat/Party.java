package com.yum.yumyums.entity.chat;

import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.enums.PayType;
import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.enums.RandomType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Party {
	@Id
	@Column(length = 50)
	private String id;

	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(50)", nullable = false)
	private PayType payType;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(50)")
	private RandomType randomType;

	@Column(columnDefinition = "boolean DEFAULT true", nullable = false)
	private boolean isActive;

	public PartyDTO entityToDto() {
		PartyDTO partyDTO = new PartyDTO();
		partyDTO.setId(id);
		partyDTO.setStoreDTO(store.entityToDto());
		partyDTO.setPayType(payType);
		partyDTO.setRandomType(randomType);
		partyDTO.setActive(isActive);

		return partyDTO;
	}

	// 생성 메소드
	public static Party createParty(String partyId, Store store, PayType payType) {
		Party party = new Party();
		party.setId(partyId);
		party.setStore(store);
		party.setPayType(payType);
		party.setActive(true);

		return party;
	}

	public static Party createPartyByPartyDTO(PartyDTO partyDTO) {
		Party party = new Party();
		party.setId(partyDTO.getId());
		party.setStore(partyDTO.getStoreDTO().dtoToEntity());
		party.setPayType(partyDTO.getPayType());
		party.setRandomType(partyDTO.getRandomType());
		party.setActive(true);

		return party;
	}
}
