package com.yum.yumyums.service.chat;

import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.dto.user.MemberDTO;

public interface PartyService {

	//URL 관련
	String generateInviteUrl(String partyId);
	String getPartyIdByInviteUrlParam(String encryptedPartyId);

	//파티 관리 (생성,삭제,추가)
	String createParty(PartyDTO partyDTO, MemberDTO memberDTO, String storeName);
	String addMemberToParty(String encryptedPartyId, MemberDTO memberDTO, boolean isPartyLeader);
	void deleteParty(String encryptedPartyId, MemberDTO memberDTO);
	void deleteMemberToParty(String encryptedPartyId, MemberDTO memberDTO, boolean isPartyLeader);

	//select
	String findEncryptedPartyIdByMemberId(MemberDTO memberDTO);
	PartyDTO findParty(String encryptedPartyId);


	//검증 (DB 데이터 확인)
	boolean isMemberInActiveParty(MemberDTO memberDTO);
	boolean isThisPartyMember(String partyId, MemberDTO memberDTO);
	boolean isThisPartyLeader(String encryptedPartyId, MemberDTO memberDTO);
	boolean isPartyMemberFull(String encryptedPartyId);
}
