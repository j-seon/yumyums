package com.yum.yumyums.entity.chat;

import com.yum.yumyums.dto.FaqDTO;
import com.yum.yumyums.dto.chat.ChatMemberDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.user.Member;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
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

	@Column(name="member_saved_room_name")
	private String memberSavedRoomName;

	public ChatMemberDTO entityToDto() {

		MemberDTO memberDTO=new MemberDTO();
		ChatMemberDTO chatMemberDTO = new ChatMemberDTO();
		chatMemberDTO.setId(this.getId());
		chatMemberDTO.setChat(this.getChat().entityToDto());
		chatMemberDTO.setMember(memberDTO.toMemberDTO(this.getMember()));
		chatMemberDTO.setMemberSavedRoomName(this.getMemberSavedRoomName());


		return chatMemberDTO;
	}
}
