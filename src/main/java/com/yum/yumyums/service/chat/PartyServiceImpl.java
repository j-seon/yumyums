package com.yum.yumyums.service.chat;

import com.yum.yumyums.APIGateway;
import com.yum.yumyums.entity.chat.Party;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.repository.chat.PartyRepository;
import com.yum.yumyums.vo.SecureUtil;
import org.springframework.stereotype.Service;

import static com.yum.yumyums.enums.FixUrl.SITE_LINK;

@Service
public class PartyServiceImpl implements PartyService {

	private final APIGateway apiGateway;
	private final PartyRepository partyRepository;

	public PartyServiceImpl(APIGateway apiGateway, PartyRepository partyRepository) {
		this.apiGateway = apiGateway;
		this.partyRepository = partyRepository;
	}

	@Override
	public String generateInviteUrlByPartyId(String memberId) {
		String encryptMemberId = SecureUtil.calcEncrypt(memberId);
		return SITE_LINK.getUrl() + encryptMemberId;
	}

	@Override
	public String getPartyIdByInviteUrlParam(String param) {
		return SecureUtil.calcDecrypt(param);
	}

	@Override
	public Party addMemberToParty(Party partyId, Member member, String param) {
		String memberId = getPartyIdByInviteUrlParam(param);
		if(apiGateway.isValidMember(memberId)) {

		}

		return null;
	}

	@Override
	public Party deleteMemberToParty(Party partyId, Member member) {
		return null;
	}

}
