package com.yum.yumyums.dto.chat;

import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.chat.Party;
import com.yum.yumyums.entity.chat.PartyMember;
import com.yum.yumyums.entity.user.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartyMemberDTO {

	private int id = -1;
	private PartyDTO partyDTO = null;
	private MemberDTO memberDTO = null;
	private boolean isPartyLeader = false;

	public PartyMember dtoToEntity() {
		PartyMember partyMember = PartyMember.createPartyMember(Member.dtoToEntity(memberDTO), partyDTO.dtoToEntity(), isPartyLeader);
		partyMember.setId(getId());
		return partyMember;
	}
}
