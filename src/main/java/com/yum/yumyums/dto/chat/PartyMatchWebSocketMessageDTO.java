package com.yum.yumyums.dto.chat;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class PartyMatchWebSocketMessageDTO {
	private String action;
	private String memberId;
	private boolean leaderChange = false;
	private String newLeaderMemberId;


	public PartyMatchWebSocketMessageDTO(String action, String memberId) {
		this.action = action;
		this.memberId = memberId;
	}
}
