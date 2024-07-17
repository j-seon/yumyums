package com.yum.yumyums.entity.chat;

import com.yum.yumyums.dto.chat.PartyMemberDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.user.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class PartyMember {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "party_id", nullable = false)
	private Party party;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(columnDefinition = "boolean DEFAULT false", nullable = false)
	private boolean isPartyLeader = false;

	public PartyMemberDTO entityToDto() {
		PartyMemberDTO partyMemberDTO = new PartyMemberDTO();
		partyMemberDTO.setId(id);
		partyMemberDTO.setPartyDTO(party.entityToDto());
		partyMemberDTO.setMemberDTO(MemberDTO.toMemberDTO(member));
		partyMemberDTO.setPartyLeader(isPartyLeader);

		return partyMemberDTO;
	}

}
