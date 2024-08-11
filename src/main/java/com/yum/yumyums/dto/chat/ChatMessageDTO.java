package com.yum.yumyums.dto.chat;

import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.chat.ChatMember;
import com.yum.yumyums.entity.chat.ChatMessage;
import com.yum.yumyums.entity.user.Member;
import lombok.Data;

@Data
public class ChatMessageDTO {

    private int id;
//    private ChatMemberDTO chatMember;
    private MemberDTO member;

    private String content;
    private ChatDTO chat;

    public ChatMessage dtoToEntity() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(this.getId());
        chatMessage.setContent(this.getContent());
        chatMessage.setMember(Member.dtoToEntity(this.getMember()));
        chatMessage.setChat(this.getChat().dtoToEntity());
        return  chatMessage;
    }
}
