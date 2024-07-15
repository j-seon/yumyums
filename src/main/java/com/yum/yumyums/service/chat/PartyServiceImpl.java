package com.yum.yumyums.service.chat;

import com.yum.yumyums.entity.chat.Party;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.vo.SecureUtil;
import org.springframework.stereotype.Service;

import static com.yum.yumyums.enums.FixUrl.SITE_LINK;

@Service
public class PartyServiceImpl implements PartyService {

	@Override
	public String generateInviteUrlByMemberId(String memberId) {
		String encryptMemberId = SecureUtil.calcEncrypt(memberId);
		return SITE_LINK.getUrl() + encryptMemberId;
	}

	@Override
	public String getMemberIdByInviteUrlParam(String param) {
		return SecureUtil.calcDecrypt(param);
	}

	@Override
	public Party addMemberToParty(Party partyId, Member member) {
		return null;
	}

	@Override
	public Party deleteMemberToParty(Party partyId, Member member) {
		return null;
	}

}
