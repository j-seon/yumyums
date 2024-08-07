package com.yum.yumyums.entity.chat;

import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.enums.PayType;
import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.enums.RandomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
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


	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
	private LocalDateTime createTime = LocalDateTime.now();

	@Column(columnDefinition = "INT DEFAULT 30", nullable = false)
	private int maxMemberCount = 30;


	@Column(columnDefinition = "boolean DEFAULT true", nullable = false)
	private boolean isActive = true; //활성화 여부 (모집중)

	@Column(columnDefinition = "BOOLEAN DEFAULT false", nullable = false)
	private boolean isMatching = false; // 랜덤매칭인 파티



	public PartyDTO entityToDto() {
		PartyDTO partyDTO = new PartyDTO();
		partyDTO.setId(id);
		partyDTO.setStoreDTO(store.entityToDto());
		partyDTO.setPayType(payType);
		partyDTO.setRandomType(randomType);
		partyDTO.setCreateTime(createTime);
		partyDTO.setMaxMemberCount(maxMemberCount);

		partyDTO.setActive(isActive);
		partyDTO.setMatching(isMatching);

		return partyDTO;
	}

	// 생성 메소드
	public static Party createParty(String partyId, Store store, PayType payType, int maxMemberCount) {
		Party party = new Party();
		party.setId(partyId);
		party.setStore(store);
		party.setPayType(payType);
		party.setActive(true);
		party.setCreateTime(LocalDateTime.now());
		party.setMaxMemberCount(maxMemberCount);

		return party;
	}

}
