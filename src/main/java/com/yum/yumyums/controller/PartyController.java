package com.yum.yumyums.controller;

import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.dto.chat.PartyMatchWebSocketMessageDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.enums.FixUrl;
import com.yum.yumyums.enums.PayType;
import com.yum.yumyums.enums.RandomType;
import com.yum.yumyums.service.chat.PartyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.yum.yumyums.util.SessionUtil.*;


@Controller
@RequestMapping("/party")
@RequiredArgsConstructor
public class PartyController {
	/**
	 * GET	/party : 파티 index (+ 파티 생성, 파티진입 선택. JS로 클릭시 페이지 변환)
	 * POST	/party : 파티 생성 -> 파티조회 페이지로 /party/${id}
	 * GET	/party/${partyId} : 파티 조회(비회원->로그인 페이지 / 파티원X -> 파티참가요청 페이지)
	 * POST	/party/${partyId} : 파티 가입 -> 파티조회 페이지로 /party/${id}
	 * DELETE /party/${partyId} : 파티탈퇴 (파티장탈퇴 = 파티삭제)
	**/

	private final SimpMessagingTemplate messagingTemplate;
	private final PartyService partyService;

	// 파티 홈페이지 조회
	@GetMapping
	public String getPartyHomePage(final HttpServletRequest request, Model model, TemplateData templateData, @RequestParam(required = false) String targetPage) {
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute(MEMBER_DTO_SESSION_ATTRIBUTE_NAME);

		//== 유효성 검사 ==//
		//소비자 회원으로 로그인중이지 않다면
		if (!isLoginAsMember(session)) {
			return "redirect:/login"; // 로그인 페이지로 이동
		}

		// 이미 파티에 가입중이라면
		String encryptedAlreadyJoinPartyID = partyService.findEncryptedPartyIdByMemberId(memberDTO);
		if (!encryptedAlreadyJoinPartyID.isEmpty()) {
			templateData.setViewPath("party/party_home_have_party");
			model.addAttribute("partyId", encryptedAlreadyJoinPartyID);
			model.addAttribute("templateData",templateData);
			return "template"; // "내파티보기" 만 있는 파티 홈페이지로 이동
		}

		//== 유효성 검사 ajax ==//
		//넘어가려는 페이지가 존재한다면 (인덱스에서 ajax로 넘어왔다면)
		if(targetPage != null) {

			// 이미 파티에 소속돼있다면
			String encryptedPartyId = partyService.findEncryptedPartyIdByMemberId(memberDTO);
			if (!encryptedPartyId.equals("")) {
				templateData.setUrl("/party/" + encryptedPartyId);
				return "inc/redirect"; // 해당 파티 상세페이지로 이동
				// ajax를 통해서 오기때문에 리다이렉트 페이지로 이동시켜 URL 이동
			}

			//파티 매칭 페이지로 이동한다면
			if(targetPage.equals("party_match")) {
				templateData.setUrl("/party/match");
				return "inc/redirect"; // 파티 매칭 url로 이동
			}

			//파티 생성 페이지로 이동한다면
			if(targetPage.equals("create_party")) {
				//파티 생성을 위한 변수들 전송
				model.addAttribute("partyDTO", new PartyDTO());
				model.addAttribute("storeName", new String());

				// 페이지에 값을 띄우기위한 이넘 값을 모델에 추가
				model.addAttribute("payTypes", PayType.values());
				model.addAttribute("randomTypes", RandomType.values());

				// 각 파티의 종류별 최대 인원을 생성 후 저장
				List<Integer> invitePartyMaxMemberCount = IntStream.rangeClosed(2, 30).boxed().collect(Collectors.toList());
				List<Integer> randomPartyMaxMemberCount = IntStream.rangeClosed(2, 4).boxed().collect(Collectors.toList());

				model.addAttribute("invitePartyMaxMemberCount", invitePartyMaxMemberCount);
				model.addAttribute("randomPartyMaxMemberCount", randomPartyMaxMemberCount);
			}

			//페이지 이동
			templateData.setViewPath("party/" + targetPage);
			return "party/" + targetPage; //연관된 페이지로 이동 (파티 들어가기 or 파티 만들기)
		}


		//== 비즈니스 로직 ==//
		// 파티 홈페이지로 이동
		templateData.setViewPath("party/party_home");
		model.addAttribute("templateData",templateData);
		return "template";
	}

	//파티 생성
	@PostMapping
	public String createParty(final HttpServletRequest request, Model model, PartyDTO partyDTO, String storeName) {
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute(MEMBER_DTO_SESSION_ATTRIBUTE_NAME);

		//== 유효성 검사 ==//
		//소비자 회원으로 로그인중이지 않다면
		if (!isLoginAsMember(session)) {
			return "redirect:/login"; // 로그인 페이지로 이동
		}

		//== 비즈니스 로직 ==//
		//파티 생성
		String encryptedPartyId = partyService.createParty(partyDTO, memberDTO, storeName);

		// 파티생성에 실패했다면
		if(encryptedPartyId.isEmpty()) {
			model.addAttribute("partyDTO", new PartyDTO());
			return "redirect:/party";
		}

		return "redirect:/party/" + encryptedPartyId;
	}

	//파티 검색
	@GetMapping("/{encryptedPartyId}")
	public String findById(@PathVariable String encryptedPartyId, final HttpServletRequest request, Model model, TemplateData templateData) {
		HttpSession session = request.getSession();

		//== 유효성 검사 ==//
		//소비자 회원으로 로그인중이지 않다면
		if (!isLoginAsMember(session)) {
			return "redirect:/login"; // 로그인 페이지로 이동
		}

		//회원, 파티 정보값 가져오기
		MemberDTO memberDTO = (MemberDTO) session.getAttribute(MEMBER_DTO_SESSION_ATTRIBUTE_NAME);
		PartyDTO partyDTO = partyService.findParty(encryptedPartyId);
		model.addAttribute("partyDTO", partyDTO);



		//== 비즈니스 로직==//
		//해당 파티의 파티원이 아니라면
		if(!partyService.isThisPartyMember(encryptedPartyId, memberDTO)) {

			//다른 파티에 소속돼있다면
			String encryptedAlreadyJoinPartyId = partyService.findEncryptedPartyIdByMemberId(memberDTO);
			if (!encryptedAlreadyJoinPartyId.isEmpty()) {
				// 해당 파티의 정보값을 저장해서 넘김
				PartyDTO alreadyJoinPartyDTO = partyService.findParty(encryptedAlreadyJoinPartyId);
				model.addAttribute("partyDTO", alreadyJoinPartyDTO);
				return "redirect:/party/" + encryptedAlreadyJoinPartyId; //소속된 파티의 상세 페이지로 이동
			}

			//다른 파티에 소속돼있지 않다면 (파티에 초대받은 사람이라면)

			// 페이지에 값을 띄우기위한 이넘 값을 모델에 추가
			model.addAttribute("payTypes", PayType.values());
			model.addAttribute("randomTypes", RandomType.values());

			//초대를 받기위한 값들을 모델에 추가
			model.addAttribute("encryptedPartyId", encryptedPartyId);

			templateData.setViewPath("party/join_party");
			model.addAttribute("templateData",templateData);
			return "template";
		}

		// 파티초대 링크저장
		String SITE_LINK = FixUrl.SITE_LINK.getUrl() + "party/";
		model.addAttribute("siteLink", SITE_LINK);
		model.addAttribute("joinPartyKey", encryptedPartyId);

		// 페이지 이동

		templateData.setViewPath("party/party_detail");
		model.addAttribute("templateData",templateData);
		return "template";
	}


	//파티에 회원추가 (파티초대)
	@PostMapping("/{encryptedPartyId}")
	public String addMemberToParty(final HttpServletRequest request, @PathVariable("encryptedPartyId") String encryptedPartyId, Model model,  TemplateData templateData) {
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute(MEMBER_DTO_SESSION_ATTRIBUTE_NAME);

		//== 유효성 검사 ==//
		//소비자 회원으로 로그인중이지 않다면
		if (!isLoginAsMember(session)) {
			return "redirect:/login"; // 로그인 페이지로 이동
		}

		//이미 가입중인 파티가 있다면
		String encryptedAlreadyJoinPartyID = partyService.findEncryptedPartyIdByMemberId(memberDTO);
		if (!encryptedAlreadyJoinPartyID.isEmpty()) {
			templateData.setUrl("/party/" + encryptedAlreadyJoinPartyID);
			return "inc/redirect"; // 해당 파티 상세페이지로 이동
		}

		//== 비즈니스 로직 ==//
		// 파티 초대를 할 수 없는 상황이라면 (최대 인원 초과)
		if (partyService.isPartyMemberFull(encryptedPartyId)) {
			return "파티 인원이 가득 찼습니다.";
		}

		partyService.addMemberToParty(encryptedPartyId, memberDTO, false);
		return "redirect:/party/" + encryptedPartyId; //파티 조회 페이지로 이동
	}

	//파티 탈퇴
	@DeleteMapping("/{encryptedPartyId}")
	public String deletePartyOrPartyMember(final HttpServletRequest request, @PathVariable("encryptedPartyId") String encryptedPartyId, Model model,  TemplateData templateData) {
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute(MEMBER_DTO_SESSION_ATTRIBUTE_NAME);

		//== 유효성검사 ==//
		//소비자 회원으로 로그인중이지 않다면
		if (!isLoginAsMember(session)) {
			return "redirect:/login"; // 로그인 페이지로 이동
		}


		//== 비즈니스 로직 ==//
		//파티장이라면
		if (partyService.isThisPartyLeader(encryptedPartyId, memberDTO)) {
			PartyDTO partyDTO = partyService.findParty(encryptedPartyId);

			//파티원이 한명이라면
			if (partyDTO.getPartyMemberCount() <= 1) {
				//파티 삭제
				partyService.deleteParty(encryptedPartyId, memberDTO);
				templateData.setUrl("/party");
				return "inc/redirect"; // 해당 파티 상세페이지로 이동
			}

			//파티원이 여러명이라면, 파티 탈퇴 후 위임
			partyService.deleteMemberToParty(encryptedPartyId, memberDTO, true);
			templateData.setUrl("/party");

			return "inc/redirect"; // 해당 파티 상세페이지로 이동
		}


		// 웹소켓으로 파티원들에게 탈퇴한 사용자 알림
		messagingTemplate.convertAndSend("/topic/party/" + encryptedPartyId,
				new PartyMatchWebSocketMessageDTO("leaveParty", memberDTO.getMemberId()));

		//파티장이 아니라면 파티탈퇴
		partyService.deleteMemberToParty(encryptedPartyId, memberDTO, true);
		templateData.setUrl("/party");
		return "inc/redirect"; // 해당 파티 상세페이지로 이동
	}

}
