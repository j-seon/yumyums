package com.yum.yumyums.service.chat;

import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.dto.user.MemberDTO;

public interface PartyService {

	//URL 관련
	String generateInviteUrl(String partyId);
	String getPartyIdByInviteUrlParam(String encryptedPartyID);

	//파티 관리 (생성,삭제,추가)
	String createParty(PartyDTO partyDTO, MemberDTO memberDTO, String storeName);
	String addMemberToParty(String encryptedPartyID, MemberDTO memberDTO, boolean isPartyLeader);
	String deleteParty(String encryptedPartyID, MemberDTO memberDTO);
	String deleteMemberToParty(String encryptedPartyID, MemberDTO memberDTO);

	//select
	String findEncryptedPartyIDByMemberId(MemberDTO memberDTO);
	PartyDTO findParty(String encryptedPartyID);

	//검증 (DB 데이터 확인)
	boolean isMemberInActiveParty(MemberDTO memberDTO);
	boolean isPartyMember(String partyId, MemberDTO memberDTO);
}
