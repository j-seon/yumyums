package com.yum.yumyums.entity.chat;

import com.yum.yumyums.entity.user.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ChatMember {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "chat_id")
	private Chat chat;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
}
