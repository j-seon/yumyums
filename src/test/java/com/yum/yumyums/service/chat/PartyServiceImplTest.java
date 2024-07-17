package com.yum.yumyums.service.chat;

import com.yum.yumyums.entity.user.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static com.yum.yumyums.enums.FixUrl.SITE_LINK;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PartyServiceImplTest {

	@InjectMocks
	private PartyServiceImpl partyService;

	//TODO given 파티로 변경해야함...
	@Test
	@DisplayName("회원 아이디를 넣으면 초대URL을 반환해야 합니다")
	public void generateInviteUrlByMemberId() throws Exception {
	    //given
		String memberId = "member1";
		String encryptMemberId = "bWVtYmVyMQ==";

		Member member = new Member();
		member.setId(memberId);

	    //when
		String url = partyService.generateInviteUrl(member.getId());

	    //then
		assertThat(url).isEqualTo(SITE_LINK.getUrl() + encryptMemberId);

	}


	//TODO given 파티로 변경해야함...
	@Test
	@DisplayName("Url 파라미터를 넣으면 회원의 id값을 반환해야 합니다")
	public void getMemberIdByInviteUrlParam() throws Exception {
	    //given
		String memberId = "member1";
		String encryptMemberId = "bWVtYmVyMQ==";

		Member member = new Member();
		member.setId(memberId);

		//when
		String decryptMemberId = partyService.getPartyIdByInviteUrlParam(encryptMemberId);

		//then
		assertThat(decryptMemberId).isEqualTo(memberId);

	}

	@Test
	void addMemberToParty() {
	}

	@Test
	void deleteMemberToParty() {
	}
}