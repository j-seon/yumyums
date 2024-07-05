package com.yum.yumyums.entity.chat;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ChatMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "chat_member_id")
	private ChatMember chatMember;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;
}
