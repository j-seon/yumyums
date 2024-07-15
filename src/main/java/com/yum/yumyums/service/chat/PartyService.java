package com.yum.yumyums.service.chat;

import com.yum.yumyums.entity.chat.Party;
import com.yum.yumyums.entity.user.Member;

public interface PartyService {

	public String generateInviteUrlByMemberId(String memberId);

	public String getMemberIdByInviteUrlParam(String param);

	public Party addMemberToParty(Party partyId, Member member);

	public Party deleteMemberToParty(Party partyId, Member member);

}
