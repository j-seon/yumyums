package com.yum.yumyums.dto.chat;

import com.yum.yumyums.entity.chat.ChatMember;
import com.yum.yumyums.entity.chat.ChatMessage;
import lombok.Data;

@Data
public class ChatMessageDTO {

    private int id;
    private ChatMemberDTO chatMember;
    private String content;
    private ChatDTO chat;

    public ChatMessage dtoToEntity() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(this.getId());
        chatMessage.setContent(this.getContent());
        chatMessage.setChatMember(this.getChatMember().dtoToEntity());
        chatMessage.setChat(this.getChat().dtoToEntity());
        return  chatMessage;
    }
}
