package com.yum.yumyums.controller;

import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.enums.PayType;
import com.yum.yumyums.enums.RandomType;
import com.yum.yumyums.service.chat.PartyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

		// 이미 파티에 소속돼있다면
		String encryptedPartyID = partyService.findEncryptedPartyIDByMemberId(memberDTO);
		if (encryptedPartyID != null) {
			return "redirect:/party/" + encryptedPartyID; // 해당 파티 상세페이지로 이동
		}

		//넘어가려는 페이지가 존재한다면 (인덱스에서 뭔가 해서 넘어왔다면)
		if(targetPage != null) {
			//파티 생성 페이지로 이동한다면
			if(targetPage.equals("create_party")) {
				//파티 생성을 위한 변수들 전송
				model.addAttribute("partyDTO", new PartyDTO());
				model.addAttribute("storeName", new String());

				// 페이지에 값을 띄우기위한 이넘 값을 모델에 추가
				model.addAttribute("payTypes", PayType.values());
				model.addAttribute("randomTypes", RandomType.values());
			}

			//페이지 이동
			templateData.setViewPath("party/" + targetPage);
			model.addAttribute("templateData",templateData);
			return "template"; //연관된 페이지로 이동 (파티 들어가기 or 파티 만들기)
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
		String encryptedPartyID = partyService.createParty(partyDTO, memberDTO, storeName);

		// 파티생성에 실패했다면
		if(encryptedPartyID == null) {
			model.addAttribute("partyDTO", new PartyDTO());
			return "redirect:/party";
		}

		return "redirect:/party/" + encryptedPartyID;
	}

	//파티 검색
	@GetMapping("/{encryptedPartyID}")
	public String findById(@PathVariable String encryptedPartyID, final HttpServletRequest request, Model model,  TemplateData templateData) {
		HttpSession session = request.getSession();

		//== 유효성 검사 ==//
		//소비자 회원으로 로그인중이지 않다면
		if (!isLoginAsMember(session)) {
			return "redirect:/login"; // 로그인 페이지로 이동
		}

		//== 비즈니스 로직==//
		//회원, 파티 정보값 가져오기
		MemberDTO memberDTO = (MemberDTO) session.getAttribute(MEMBER_DTO_SESSION_ATTRIBUTE_NAME);
		PartyDTO partyDTO = partyService.findParty(encryptedPartyID);
		model.addAttribute("partyDTO", partyDTO);

		//파티원이 아니라면
//		if(!partyService.isPartyMember(encryptedPartyID, memberDTO)) {
//			templateData.setViewPath("party/join_party");
//			model.addAttribute("templateData", templateData);
//			return "template"; //파티초대 페이지로 이동
//		}

		// 페이지 이동
		templateData.setViewPath("party/party_detail");
		model.addAttribute("templateData", templateData);
		return "template"; //파티상세 페이지로 이동
	}

//
//	//파티에 회원추가 (파티초대)
//	@PostMapping("/${partyId}")
//	public String addMemberToParty(final HttpServletRequest request, @RequestParam("partyId") String partyId) {
//		HttpSession session = request.getSession();
//		String memberId = (String) session.getAttribute(MEMBER_DTO_SESSION_ATTRIBUTE_NAME);
//
//		//소비자 회원으로 로그인중이지 않다면
//		if (!isLoginAsMember(session)) {
//			return "redirect:/login"; // 로그인 페이지로 이동
//		}
//
//		//TODO 파티초대 로직
//		//TODO partyId 복호화해서 다시저장.
//
//		//TODO 세션.멤버id로 파티 검색 + isActive가 true인 파티가 존재하는지 여부를 확인.
////		if (partyService.isMemberInActiveParty(memberId)) {
////			// TODO 존재한다면 memberId로 존재하는 파티조회
////			// TODO redirect deletePartyOrPartyMember 함수호출
////		}
//
//		//TODO 파티가입 : artyId, memberId를 이용해 파티원 테이블에 insert
//		//TODO partyId 값을 세션에넣고 party/${partyId}리다이렉트
//
//		// partyService.addMemberToParty() 이용하기 -> PartyDTO를 반환??
//
//		return "redirect:/party/" + partyId; //파티 조회 페이지로 이동
//	}
//
//	//파티 탈퇴
//	@DeleteMapping("/${partyId}")
//	public String deletePartyOrPartyMember(final HttpServletRequest request, @RequestParam("partyId") String partyId) {
//		HttpSession session = request.getSession();
//
//		//소비자 회원으로 로그인중이지 않다면
//		if (!isLoginAsMember(session)) {
//			return "redirect:/login"; // 로그인 페이지로 이동
//		}
//
//		//TODO 파티탈퇴 로직제작
//
//		//TODO partyId, memberId를 통해 파티장인지 확인 -> where문으로 걸고 해당 컬럼의 파티장여부가 true인지 확인
//		//TODO 파티원 Entity제작 -> 테이블 구성까지 제작
//		//TODO 파티장이라면 방을 폭파시킨다 (파티원목록에서 partyId로 where문 걸고 전부 딜리트 + 파티도 where로 딜리트)
//		//TODO 파티장이 아니라면 (파티원목록에서 partyId, memberId where문걸고 딜리트)
//
//		//TODO 파티탈퇴후 세션에서 파티ID 등 파티정보값 모두 삭제
//
//		return null;
//	}



}
