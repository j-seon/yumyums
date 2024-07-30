package com.yum.yumyums.dto.chat;

import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.chat.Party;
import com.yum.yumyums.entity.chat.PartyMember;
import com.yum.yumyums.entity.user.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PartyMemberDTO {

	private int id = -1;
	private PartyDTO partyDTO = null;
	private MemberDTO memberDTO = null;
	private boolean isPartyLeader = false;
	private LocalDateTime joinTime = LocalDateTime.now();

	public PartyMember dtoToEntity() {
		PartyMember partyMember = PartyMember.createPartyMember(Member.dtoToEntity(memberDTO), partyDTO.dtoToEntity(), isPartyLeader);
		partyMember.setId(id);
		partyMember.setJoinTime(joinTime);
		return partyMember;
	}
}
