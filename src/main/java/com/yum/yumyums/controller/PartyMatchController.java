package com.yum.yumyums.controller;

import com.yum.yumyums.dto.chat.MatchRequestDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PartyMatchController {

	private final SimpMessagingTemplate messagingTemplate;

	public PartyMatchController(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@MessageMapping("/match")
	public void requestMatch(@RequestBody MatchRequestDTO matchRequestDTO) {
		// 매칭 로직을 구현합니다. 예를 들어, 매칭 대기열에 추가하고,
		// 매칭이 완료되면 결과를 클라이언트에 전송합니다.

		// 매칭 요청을 처리하는 예시 로직
		// 이 부분은 실제 매칭 로직으로 대체되어야 합니다.
		String matchedUserId = performMatching(matchRequestDTO);

		// 매칭 결과를 클라이언트에 전송
		messagingTemplate.convertAndSend("/matching/results", matchedUserId);
	}

	private String performMatching(MatchRequestDTO matchRequestDTO) {
		// 매칭 로직 구현 (예시로 임의의 사용자 ID 반환)
		return "MatchedUser123"; // 실제 매칭 로직에 따라 변경
	}
}
