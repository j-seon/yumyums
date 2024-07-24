package com.yum.yumyums.service.chat;

import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.dto.chat.PartyMemberDTO;
import com.yum.yumyums.dto.seller.SellerDTO;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.chat.PartyMember;
import com.yum.yumyums.enums.PayType;
import com.yum.yumyums.enums.RandomType;
import com.yum.yumyums.repository.chat.PartyMemberRepository;
import com.yum.yumyums.repository.chat.PartyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.yum.yumyums.enums.FixUrl.SITE_LINK;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class PartyServiceImplTest {
	@Mock
	private PartyRepository partyRepository;
	@Mock
	private PartyMemberRepository partyMemberRepository;

	@InjectMocks
	private PartyServiceImpl partyService;

	PartyDTO partyDTO;
	MemberDTO memberDTO;
	String partyId;
	String encryptedPartyId;
	PartyMemberDTO partyMemberDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		partyId = "202407192046570070854488e08184";
		encryptedPartyId = "MjAyNDA3MTkyMDQ2NTcwMDcwODU0NDg4ZTA4MTg0";

		SellerDTO sellerDTO = new SellerDTO();
		sellerDTO.setSellerId("seller1");

		StoreDTO storeDTO = new StoreDTO();
		storeDTO.setStoreId(1);
		storeDTO.setSellerDTO(sellerDTO);

		memberDTO = new MemberDTO();
		memberDTO.setMemberId("user01");

		partyMemberDTO = new PartyMemberDTO();
		partyMemberDTO.setMemberDTO(memberDTO);

		List<PartyMemberDTO> partyMemberDTOs = new ArrayList<>();
		partyMemberDTOs.add(partyMemberDTO);

		partyDTO = new PartyDTO();
		partyDTO.setId(partyId);
		partyDTO.setPayType(PayType.DOUTCH);
		partyDTO.setStoreDTO(storeDTO);
		partyDTO.setActive(true);
		partyDTO.setRandomType(RandomType.DICE);
	}

	@Test
	@DisplayName("파티 아이디를 넣으면 초대URL을 반환해야 합니다")
	public void generateInviteUrlByMemberId() throws Exception {
	    //when
		String url = partyService.generateInviteUrl(partyId);

	    //then
		assertThat(url).isEqualTo(SITE_LINK.getUrl() + encryptedPartyId);
	}

	@Test
	@DisplayName("Url 파라미터를 넣으면 회원의 id값을 반환해야 합니다")
	public void getMemberIdByInviteUrlParam() throws Exception {
		//when
		String decryptMemberId = partyService.getPartyIdByInviteUrlParam(encryptedPartyId);

		//then
		assertThat(decryptMemberId).isEqualTo(partyId);

	}

	@Test
	@DisplayName("암호화된 파티ID를 넘기면 DB에서 파티를 찾아 PartyDTO를 반환해야 합니다")
	public void findParty() {
		//TODO : 테스트코드 다시 작성 필요
		//given
		//DB에 존재하는 파티아이디 값을 집어넣음
		partyId = "202407172046569917024cc3db7034";
		partyDTO.setId(partyId);

		encryptedPartyId = "MjAyNDA3MTcyMDQ2NTY5OTE3MDI0Y2MzZGI3MDM0";

		when(partyRepository.findById(eq(partyId))).thenReturn(Optional.of(partyDTO.dtoToEntity()));
		when(partyMemberRepository.findAllByPartyId(anyString())).thenReturn(new ArrayList<>());

		//when
		PartyDTO findValuePartyDTO = partyService.findParty(encryptedPartyId); // 메서드 호출

		//then
		assertNotNull(findValuePartyDTO); // 반환된 PartyDTO가 null이 아님을 확인
		assertEquals(partyDTO.getId(), findValuePartyDTO.getId()); // ID가 일치하는지 확인
		assertEquals(partyDTO.getPayType(), findValuePartyDTO.getPayType()); // 결제 타입이 일치하는지 확인
		assertEquals(partyDTO.isActive(), findValuePartyDTO.isActive()); // 활성 상태가 일치하는지 확인

		//파티 멤버 리스트도 확인 (필요에 따라 추가)
		verify(partyRepository, times(1)).findById(partyId); // findById 메서드가 한 번 호출되었는지 확인
		verify(partyMemberRepository, times(1)).findAllByPartyId(partyId); // findAllByPartyId 메서드가 한 번 호출되었는지 확인
	}

	@Test
	@DisplayName("파티 생성 성공 테스트")
	public void createParty() {
		//TODO : 테스트코드 다시 작성 필요
		//when
		String storeName = "홍길동 김밥";
		String resultPartyID = partyService.createParty(partyDTO, memberDTO, storeName);

		//then
		assertNotNull(resultPartyID);
		verify(partyService, times(1)).createPartyRoom(any(PartyDTO.class));
		verify(partyService, times(1)).addMemberToParty(eq(encryptedPartyId), any(MemberDTO.class), eq(true));
	}

}