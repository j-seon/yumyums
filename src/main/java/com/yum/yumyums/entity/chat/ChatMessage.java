package com.yum.yumyums.entity.chat;

import com.yum.yumyums.dto.FaqDTO;
import com.yum.yumyums.dto.chat.ChatMessageDTO;
import com.yum.yumyums.entity.user.Member;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
public class ChatMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	@ManyToOne
	@JoinColumn(name = "chat_id")
	private Chat chat;

	public ChatMessageDTO entityToDto() {

		ChatMessageDTO chatMessageDTO = new ChatMessageDTO();

		chatMessageDTO.setMember(this.getMember().entityToDto());
		chatMessageDTO.setChat(this.chat.entityToDto());
		chatMessageDTO.setId(this.getId());
		chatMessageDTO.setContent(this.getContent());

		return chatMessageDTO;
	}
}
