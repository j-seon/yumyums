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

    public ChatMessage toDtoToEntity() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(this.getId());
        chatMessage.setContent(this.getContent());
        chatMessage.setChatMember(this.getChatMember().toDtoToEntity());
        chatMessage.setChat(this.getChat().toDtoToEntity());
        return  chatMessage;
    }
}
