package com.yum.yumyums.entity.chat;

import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.enums.PayType;
import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.enums.RandomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Party {
	@Id
	@Column(length = 50)
	private String id;

	@ManyToOne
	@JoinColumn(name = "shore_id")
	private Store store;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(50)", nullable = false)
	private PayType payType;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(50)", nullable = false)
	private RandomType randomType;

	@Column(columnDefinition = "boolean DEFAULT true", nullable = false)
	private boolean isActive;

	@OneToMany(mappedBy = "party")
	private List<PartyMember> partyMembers;

	public PartyDTO entityToDto() {
		PartyDTO partyDTO = new PartyDTO();
		partyDTO.setId(id);
		partyDTO.setStoreDTO(store.entityToDto());
		partyDTO.setPayType(payType);
		partyDTO.setRandomType(randomType);
		partyDTO.setActive(isActive);
		partyDTO.setPartyMemberDTOs(
				partyMembers.stream()
						.map(PartyMember::entityToDto)
						.collect(Collectors.toList())
		);

		return partyDTO;
	}
}
