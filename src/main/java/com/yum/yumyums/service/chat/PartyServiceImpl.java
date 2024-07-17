package com.yum.yumyums.service.chat;

import com.yum.yumyums.APIGateway;
import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.chat.Party;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.repository.chat.PartyRepository;
import com.yum.yumyums.vo.SecureUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.yum.yumyums.enums.FixUrl.SITE_LINK;

@Service
@RequiredArgsConstructor
public class PartyServiceImpl implements PartyService {

	private final APIGateway apiGateway;
	private final PartyRepository partyRepository;


	// [URL] URL 생성
	@Override
	public String generateInviteUrl(String partyId) {
		String encryptPartyId = SecureUtil.calcEncrypt(partyId);
		return SITE_LINK.getUrl() + encryptPartyId;
	}

	// [URL] URL로 partyId 추출
	@Override
	public String getPartyIdByInviteUrlParam(String urlParam) {
		return SecureUtil.calcDecrypt(urlParam);
	}

	// [관리] 파티 생성
	@Override
	public String createParty(PartyDTO partyDTO, MemberDTO memberDTO) {

		return null;
	}

	// [관리] 파티에 인원 추가
	@Override
	public String addMemberToParty(String partyId, MemberDTO memberDTO) {
		String encryptPartyId = getPartyIdByInviteUrlParam(partyId);
		String memberId = memberDTO.getMemberId();

		// 정상적인 회원이 아니라면
		if(!apiGateway.isValidMember(memberId)) {
			return "fail";
		}

		//그냥 partyMember 테이블에 insert하기
		//DTO를 어디에 안쓸거니 DTO수정은 필요없음.



		return null;
	}

	// [관리] 파티탈퇴 (파티장탈퇴 = 파티삭제)
	@Override
	public String deleteMemberToParty(String partyId, MemberDTO memberDTO) {
		return null;
	}


	// [select] 파티id로 파티 찾아주기
	@Override
	public PartyDTO findParty(String partyId) {
		return null;
	}

	// [select] 회원id로 파티 찾아주기
	@Override
	public PartyDTO findPartyByMemberId(MemberDTO memberDTO) {
		return null;
	}

	// [검증] 파티가 존재하는 회원인지 조회
	@Override
	public boolean isMemberInActiveParty(MemberDTO memberDTO) {
		return false;
	}

	// [검증] 해당 파티에 소속돼있는 회원인지 조회
	@Override
	public boolean isPartyMember(String partyId, MemberDTO memberDTO) {
		return false;
	}

}
