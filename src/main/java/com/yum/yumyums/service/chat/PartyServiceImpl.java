package com.yum.yumyums.service.chat;

import com.yum.yumyums.APIGateway;
import com.yum.yumyums.dto.chat.MatchRequestDTO;
import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.dto.chat.PartyMatchWebSocketMessageDTO;
import com.yum.yumyums.dto.chat.PartyMemberDTO;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.chat.Party;
import com.yum.yumyums.entity.chat.PartyMember;
import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.repository.chat.PartyMemberRepository;
import com.yum.yumyums.repository.chat.PartyRepository;
import com.yum.yumyums.util.PKGenerator;
import com.yum.yumyums.util.SecureUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.yum.yumyums.enums.FixUrl.SITE_LINK;

@Service
@RequiredArgsConstructor
public class PartyServiceImpl implements PartyService {

	private final SimpMessagingTemplate messagingTemplate;
	private final APIGateway apiGateway;
	private final PartyRepository partyRepository;
	private final PartyMemberRepository partyMemberRepository;


	// [URL] URL 생성
	@Override
	public String generateInviteUrl(String partyId) {
		String encryptedPartyId = generateInviteUrlParam(partyId);
		return SITE_LINK.getUrl() + encryptedPartyId;
	}

	// [URL] 파라미터 생성
	private String generateInviteUrlParam(String partyId) {
		return SecureUtil.calcEncrypt(partyId);
	}

	// [URL] URL로 partyId 추출
	@Override
	public String getPartyIdByInviteUrlParam(String encryptedPartyId) {
		return SecureUtil.calcDecrypt(encryptedPartyId);
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
		String encryptedPartyId = createPartyRoom(partyDTO);

		//파티장을 멤버에 추가
		String insertPartyMemberValuePartyId = addMemberToParty(encryptedPartyId, memberDTO, true);


		//정상 저장여부 체크
		if(!insertPartyMemberValuePartyId.equals(encryptedPartyId)) {
			throw new RuntimeException("파티 생성 중, 파티 리더를 DB에 저장하는데 실패했습니다.");
		}
		 return encryptedPartyId;
	}

	// [관리] 파티방 생성
	public String createPartyRoom(PartyDTO partyDTO) {
		//Entity 변환
		Party party = partyDTO.dtoToEntity();

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
	public String addMemberToParty(String encryptedPartyId, MemberDTO memberDTO, boolean isPartyLeader) {
		//파티아이디 복호화
		String partyId = getPartyIdByInviteUrlParam(encryptedPartyId);

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


		//파티에 가입한 회원 정보값을 파티 상세페이지에 웹소켓으로 전송
		messagingTemplate.convertAndSend("/topic/party/" + encryptedPartyId, partyMember.entityToDto());

		//파티 아이디를 암호화해서 리턴
		return generateInviteUrlParam(partyId);
	}

	// [관리] 파티삭제 (파티원이 파티장 한명인 파티의 파티 탈퇴)
	@Override
	@Transactional
	public void deleteParty(String encryptedPartyId, MemberDTO memberDTO) {
		//복호화
		String partyId = getPartyIdByInviteUrlParam(encryptedPartyId);

		apiGateway.deleteAllPartyCartsByPartyIdAndMemberId(memberDTO, partyId);
		partyMemberRepository.deleteByMemberId(memberDTO.getMemberId());
		partyRepository.deleteById(partyId);
	}

	// [관리] 파티탈퇴
	@Override
	@Transactional
	public void deleteMemberToParty(String encryptedPartyId, MemberDTO memberDTO, boolean isPartyLeader) {
		//탈퇴 정보값을 전송할 메세지DTO 생성
		PartyMatchWebSocketMessageDTO messageDTO = new PartyMatchWebSocketMessageDTO("leaveParty", memberDTO.getMemberId());

		//장바구니에 넣어놨던 메뉴들 모두 삭제
		String partyId = SecureUtil.calcDecrypt(encryptedPartyId);
		apiGateway.deleteAllPartyCartsByPartyIdAndMemberId(memberDTO, partyId);

		//파티탈퇴
		partyMemberRepository.deleteByMemberId(memberDTO.getMemberId());

		if(isPartyLeader) {
			// 파티의 멤버 리스트 가져오기
			PartyDTO partyDTO = findParty(encryptedPartyId);
			List<PartyMemberDTO> partyMemberDTOs = partyDTO.getPartyMemberDTOs();

			// 남아있는 멤버 중 가장 빨리 가입한 멤버 찾기
			Optional<PartyMemberDTO> newLeader = partyMemberDTOs.stream()
					.filter(partyMemberDTO -> !partyMemberDTO.getMemberDTO().getMemberId().equals(memberDTO.getMemberId())) // 탈퇴한 멤버 제외(false=제외)
					.min(Comparator.comparing(PartyMemberDTO::getJoinTime)); // 가입 시간 기준으로 최소값 찾기

			// 새로운 파티장이 있을 경우 설정
			newLeader.ifPresent(member -> {
				member.setPartyLeader(true); // 파티장 설정
				messageDTO.setLeaderChange(true); // 메세지에도 파티장 변경함을 저장
				messageDTO.setNewLeaderMemberId(newLeader.get().getMemberDTO().getMemberId()); //변경된 파티장의 ID를 저장
				partyMemberRepository.save(member.dtoToEntity()); // DTO를 엔티티로 변환하여 저장
			});

		}

		// 웹소켓으로 파티원들에게 탈퇴한 사용자 알림
		messagingTemplate.convertAndSend("/topic/party/" + encryptedPartyId, messageDTO);

	}


	// [select] 파티id로 파티 찾아주기
	@Override
	public PartyDTO findParty(String encryptedPartyId) {
		//복호화
		String partyId = getPartyIdByInviteUrlParam(encryptedPartyId);

		//파티찾기
		PartyDTO partyDTO = partyRepository.findById(partyId).get().entityToDto();

		//파티멤버 찾아넣기
		List<PartyMember> partyMemberList = partyMemberRepository.findAllByPartyId(partyDTO.getId());
		partyDTO.setPartyMembersByPartyMemberList(partyMemberList);

		return partyDTO;
	}

	// [select] 회원id로 파티 찾아, 암호화된 파티 아이디 돌려주기
	@Override
	public String findEncryptedPartyIdByMemberId(MemberDTO memberDTO) {
		Party party = partyRepository.findActivePartyByMemberId(memberDTO.getMemberId());
		if(party == null) {
			return "";
		}
		return SecureUtil.calcEncrypt(party.getId());
	}

	// [select] 파티id와 회원id로 파티의 회원 정보값을 찾아, 파티회원 DTO로 돌려주기
	public PartyMemberDTO findPartyMemberDtoByPartyIdAndMemberId(String encryptedPartyId, MemberDTO memberDTO) {
		//파티아이디 복호화
		String partyId = getPartyIdByInviteUrlParam(encryptedPartyId);

		PartyMember partyMember = partyMemberRepository.findByPartyIdAndMemberId(partyId, memberDTO.getMemberId());
		return partyMember.entityToDto();
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
	public boolean isThisPartyMember(String encryptedPartyId, MemberDTO memberDTO) {
		//파티아이디 복호화
		String partyId = getPartyIdByInviteUrlParam(encryptedPartyId);

		//Party 가져오기
		boolean isPartyMember = partyMemberRepository.existsByPartyIdAndMemberIdAndPartyIsActiveTrue(partyId, memberDTO.getMemberId());
		return isPartyMember;
	}


	// [검증] 파티장인지 조회
	@Override
	public boolean isThisPartyLeader(String encryptedPartyId, MemberDTO memberDTO) {
		//파티 아이디 복호화
		String partyId = getPartyIdByInviteUrlParam(encryptedPartyId);
		return partyMemberRepository.existsActivePartyWithLeader(partyId, memberDTO.getMemberId());
	}

	// [검증] 파티 초대가 가능한 상태인지 확인 (파티 인원수 조회)
	@Override
	public boolean isPartyMemberFull(String encryptedPartyId) {
		//파티 정보값 DB에서 불러오기
		PartyDTO partyDTO = findParty(encryptedPartyId);
		int maxPartyMemberCount = partyDTO.getMaxMemberCount();
		int nowPartyMemberCount = partyMemberRepository.countByPartyId(partyDTO.getId());

		// 파티가 가득찼다면
		if (nowPartyMemberCount + 1 > maxPartyMemberCount) {
			return true;
		}
		return false;
	}



	// [매칭] 파티 매칭 로직 (옵션을 기반으로 파티를 찾고 -> 없다면 생성 / 있다면 파티에 가입)
	@Override
	public String addPartyMemberToOptionalPartyOrCreateParty(MatchRequestDTO matchRequestDTO) {
		// storeName을 기반으로 storeId를 검색
		StoreDTO storeDTO = apiGateway.findStoreDtoByStoreName(matchRequestDTO.getStoreName());
		if (storeDTO == null) {
			// Store가 없으면 예외 처리 또는 새로운 Store 생성 로직 추가
			throw new IllegalArgumentException("Store not found: " + matchRequestDTO.getStoreName());
		}

		// 조건에 맞는 파티들 검색
		Pageable pageable = PageRequest.of(0, 1); // 첫 페이지에서 한 개의 결과만 가져옴
		List<Party> matchingPartys = partyRepository.findMatchingParty(
				storeDTO.getStoreId(),
				matchRequestDTO.getPayType(),
				matchRequestDTO.getMaxMemberCount(),
				pageable
		);

		// 가장 생성된지 오래된 하나의 값만 가져옴
		Party matchingParty = matchingPartys.isEmpty() ? null : matchingPartys.get(0);


		// 매칭된 파티값이 없다면
		if (matchingParty == null) {
			// 신규 파티를 생성
			PartyDTO partyDTO = PartyDTO.createPartyDtoFromMatchRequest(storeDTO, matchRequestDTO);
			String encryptedPartyId = createParty(partyDTO, matchRequestDTO.getMemberDTO(), matchRequestDTO.getStoreName());

			return encryptedPartyId;
		}

		//매칭된 파티가 존재한다면
		//파티에 가입시키기
		String encryptedMatchPartyId = SecureUtil.calcEncrypt(matchingParty.getId());
		addMemberToParty(encryptedMatchPartyId, matchRequestDTO.getMemberDTO(), false);

		return encryptedMatchPartyId;
	}
}
