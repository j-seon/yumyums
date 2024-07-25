package com.yum.yumyums.service.chat;

import com.yum.yumyums.APIGateway;
import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.chat.Party;
import com.yum.yumyums.entity.chat.PartyMember;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.repository.chat.PartyMemberRepository;
import com.yum.yumyums.repository.chat.PartyRepository;
import com.yum.yumyums.util.PKGenerator;
import com.yum.yumyums.util.SecureUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yum.yumyums.enums.FixUrl.SITE_LINK;

@Service
@RequiredArgsConstructor
public class PartyServiceImpl implements PartyService {

	private final APIGateway apiGateway;
	private final PartyRepository partyRepository;
	private final PartyMemberRepository partyMemberRepository;


	// [URL] URL 생성
	@Override
	public String generateInviteUrl(String partyId) {
		String encryptedPartyID = generateInviteUrlParam(partyId);
		return SITE_LINK.getUrl() + encryptedPartyID;
	}

	// [URL] 파라미터 생성
	private String generateInviteUrlParam(String partyId) {
		return SecureUtil.calcEncrypt(partyId);
	}

	// [URL] URL로 partyId 추출
	@Override
	public String getPartyIdByInviteUrlParam(String encryptedPartyID) {
		return SecureUtil.calcDecrypt(encryptedPartyID);
	}

	// [관리] 파티방 생성 + 파티에 인원 추가
	@Override
	@Transactional
	public String createParty(PartyDTO partyDTO, MemberDTO memberDTO, String storeName) {
		//storeDTO 넣기
		StoreDTO storeDTO = apiGateway.findStoreDtoByStoreName(storeName);
		partyDTO.setStoreDTO(storeDTO);
		//PK 생성
		String partyId = PKGenerator.generatePK();
		partyDTO.setId(partyId);

		//방 생성
		String encryptedPartyID = createPartyRoom(partyDTO);

		//파티장을 멤버에 추가
		String insertPartyMemberValuePartyId = addMemberToParty(encryptedPartyID, memberDTO, true);


		//정상 저장여부 체크
		if(!insertPartyMemberValuePartyId.equals(encryptedPartyID)) {
			throw new RuntimeException("파티 생성 중, 파티 리더를 DB에 저장하는데 실패했습니다.");
		}
		 return encryptedPartyID;
	}

	// [관리] 파티방 생성
	public String createPartyRoom(PartyDTO partyDTO) {
		//Entity 변환
		Party party = Party.createPartyByPartyDTO(partyDTO);

		//파티를 DB에 저장
		try {
			partyRepository.save(party);
		} catch (Exception e) {
			throw new RuntimeException("파티를 DB에 저장하는데 실패했습니다.", e);
		}
		//파티 아이디를 암호화해서 리턴
		return generateInviteUrlParam(party.getId());
	}

	// [관리] 파티에 인원 추가
	@Override
	public String addMemberToParty(String encryptedPartyID, MemberDTO memberDTO, boolean isPartyLeader) {
		//파티아이디 복호화
		String partyId = getPartyIdByInviteUrlParam(encryptedPartyID);

		//Party 가져오기
		Party party = partyRepository.findById(partyId)
				.orElseThrow(() ->
						new RuntimeException("존재하지 않는 파티에 접근 - 파티 ID: " + partyId));
				//orElseThrow = 값이 있다면 값을 반환, 없다면 예외를 던짐


		//PartyMember 생성
		Member member = Member.dtoToEntity(memberDTO);
		PartyMember partyMember = PartyMember.createPartyMember(member, party, isPartyLeader);

		//파티맴버를 DB에 저장
		try {
			partyMemberRepository.save(partyMember);
		} catch (Exception e) {
			throw new RuntimeException("파티 맴버를 DB에 저장하는데 실패했습니다.", e);
		}

		//파티 아이디를 암호화해서 리턴
		return generateInviteUrlParam(partyId);
	}

	// [관리] 파티탈퇴
	@Override
	public String deleteMemberToParty(String encryptedPartyID, MemberDTO memberDTO) {
		return null;
	}

	// [관리] 파티삭제 (파티장탈퇴 = 파티삭제)
	@Override
	public String deleteParty(String encryptedPartyID, MemberDTO memberDTO) {
		return null;
	}


	// [select] 파티id로 파티 찾아주기
	@Override
	public PartyDTO findParty(String encryptedPartyID) {
		//복호화
		String partyId = getPartyIdByInviteUrlParam(encryptedPartyID);

		//파티찾기
		PartyDTO partyDTO = partyRepository.findById(partyId).get().entityToDto();

		//파티멤버 찾아넣기
		List<PartyMember> partyMemberList = partyMemberRepository.findAllByPartyId(partyDTO.getId());
		partyDTO.setPartyMembersByPartyMemberList(partyMemberList);

		return partyDTO;
	}

	// [select] 회원id로 파티 찾아, 암호화된 파티 아이디 돌려주기
	@Override
	public String findEncryptedPartyIDByMemberId(MemberDTO memberDTO) {
		Party party = partyRepository.findActivePartyByMemberId(memberDTO.getMemberId());
		if(party == null) {
			return null;
		}
		return SecureUtil.calcEncrypt(party.getId());
	}

	// [검증] 파티가 존재하는 회원인지 조회
	@Override
	public boolean isMemberInActiveParty(MemberDTO memberDTO) {
		Party party = partyRepository.findActivePartyByMemberId(memberDTO.getMemberId());
		if(party == null) {
			return false;
		}
		return true;
	}

	// [검증] 해당 파티에 소속돼있는 회원인지 조회
	@Override
	public boolean isPartyMember(String encryptedPartyID, MemberDTO memberDTO) {
		return false;
	}

}
