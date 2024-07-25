package com.yum.yumyums.dto.chat;

import com.yum.yumyums.entity.chat.ChatMember;
import lombok.Data;

@Data
public class ChatMessageDTO {

    private int id;
    private ChatMemberDTO chatMember;
    private String content;
}
