package com.yum.yumyums.dto.chat;

import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.chat.PartyMember;
import com.yum.yumyums.entity.user.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartyMemberDTO {

	private int id;
	private PartyDTO partyDTO;
	private MemberDTO memberDTO;
	private boolean isPartyLeader;

	public PartyMember entityToDto() {
		PartyMember partyMemberDTO = new PartyMember();
		partyMemberDTO.setId(getId());
		partyMemberDTO.setParty(partyDTO.dtoToEntity());
		partyMemberDTO.setMember(Member.toSaveEntity(memberDTO));
		partyMemberDTO.setPartyLeader(isPartyLeader);

		return partyMemberDTO;
	}
}
