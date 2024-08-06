package com.yum.yumyums.controller;

import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.chat.MatchRequestDTO;
import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.dto.chat.PartyMemberDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.enums.PayType;
import com.yum.yumyums.enums.RandomType;
import com.yum.yumyums.service.chat.PartyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.yum.yumyums.util.SessionUtil.MEMBER_DTO_SESSION_ATTRIBUTE_NAME;
import static com.yum.yumyums.util.SessionUtil.isLoginAsMember;

@Controller
@RequestMapping("/party/match")
@RequiredArgsConstructor
public class MatchController {
	private final SimpMessagingTemplate messagingTemplate;
	private final PartyService partyService;

	@GetMapping
	public String getMatchPage(final HttpServletRequest request, Model model, TemplateData templateData, @RequestParam(required = false) String targetPage) {
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute(MEMBER_DTO_SESSION_ATTRIBUTE_NAME);

		//== 유효성 검사 ==//
		//소비자 회원으로 로그인중이지 않다면
		if (!isLoginAsMember(session)) {
			return "redirect:/login"; // 로그인 페이지로 이동
		}

		// 이미 파티에 소속돼있다면
		String encryptedPartyId = partyService.findEncryptedPartyIdByMemberId(memberDTO);
		if (!encryptedPartyId.equals("")) {
			return "redirect:/party/" + encryptedPartyId; // 해당 파티 상세페이지로 이동
		}


		//== 비즈니스 로직 ==//
		//파티 생성을 위한 변수들 전송
		model.addAttribute("matchRequestDTO", new MatchRequestDTO());
		model.addAttribute("storeName", new String());

		// 페이지에 값을 띄우기위한 이넘 값을 모델에 추가
		model.addAttribute("payTypes", PayType.values());
		model.addAttribute("randomTypes", RandomType.values());

		// 각 파티의 종류별 최대 인원을 생성 후 저장
		List<Integer> randomPartyMaxMemberCount = IntStream.rangeClosed(2, 4).boxed().collect(Collectors.toList());

		model.addAttribute("randomPartyMaxMemberCount", randomPartyMaxMemberCount);


		templateData.setViewPath("party/party_match");
		model.addAttribute("templateData", templateData);
		return "template"; //연관된 페이지로 이동 (파티 들어가기 or 파티 만들기)
	}

	// [웹소켓] 사용되는 메소드
	@PostMapping
	public String startPartyMatch(final HttpServletRequest request, Model model, TemplateData templateData, MatchRequestDTO matchRequestDTO) {
		//== 비즈니스 로직 ==//
		// 회원 값을 받아와 matchDTO에 저장
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute(MEMBER_DTO_SESSION_ATTRIBUTE_NAME);
		matchRequestDTO.setMemberDTO(memberDTO);

		//옵션에 맞는 파티가 존재한다면 파티에 가입후 웹소켓으로 회원값 전달, 파티가 없다면 파티 생성
		String encryptedMatchPartyId = partyService.addPartyMemberToOptionalPartyOrCreateParty(matchRequestDTO);

		return "redirect:/party/" + encryptedMatchPartyId;
	}

}