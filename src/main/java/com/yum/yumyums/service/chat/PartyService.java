package com.yum.yumyums.service.chat;

import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.entity.chat.Party;
import com.yum.yumyums.entity.user.Member;

public interface PartyService {

	public String generateInviteUrlByPartyId(String memberId);

	public String getPartyIdByInviteUrlParam(String param);

	// TODO 무슨값을 파라미터로 넘길지 고민해보기. 만약 파티라면 파티의 값은 어떻게 가져옴?
	public Party addMemberToParty(Party partyId, Member member, String param);

	public Party deleteMemberToParty(Party partyId, Member member);

	boolean isMemberInActiveParty(String memberId);

	PartyDTO findParty(String memberId);

	PartyDTO createParty(PartyDTO partyDTO, String memberId);

	boolean isPartyMember(String memberId, String partyId);
}
