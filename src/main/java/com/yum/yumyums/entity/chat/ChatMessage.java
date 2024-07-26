package com.yum.yumyums.entity.chat;

import com.yum.yumyums.dto.FaqDTO;
import com.yum.yumyums.dto.chat.ChatMessageDTO;
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
	@JoinColumn(name = "chat_member_id")
	private ChatMember chatMember;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	@ManyToOne
	@JoinColumn(name = "chat_id")
	private Chat chat;

	public ChatMessageDTO entityToDto() {

		ChatMessageDTO chatMessageDTO = new ChatMessageDTO();

		chatMessageDTO.setChatMember(this.getChatMember().entityToDto());
		chatMessageDTO.setChat(this.chat.EntityToDto());
		chatMessageDTO.setId(this.getId());
		chatMessageDTO.setContent(this.getContent());

		return chatMessageDTO;
	}
}
