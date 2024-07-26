package com.yum.yumyums.dto.chat;

import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.chat.Chat;
import com.yum.yumyums.entity.chat.ChatMember;
import com.yum.yumyums.entity.user.Member;
import lombok.Data;

@Data
public class ChatMemberDTO {

    private int id;
    private ChatDTO chat;
    private MemberDTO member;
    private String memberSavedRoomName;

    public ChatMember dtoToEntity() {
        ChatMember chatMember = new ChatMember();
        chatMember.setId(this.getId());
        chatMember.setChat(this.getChat().dtoToEntity());
        chatMember.setMemberSavedRoomName(this.getMemberSavedRoomName());
        return chatMember;
    }
}
