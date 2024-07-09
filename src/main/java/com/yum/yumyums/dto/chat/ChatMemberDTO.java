package com.yum.yumyums.dto.chat;

import com.yum.yumyums.entity.chat.Chat;
import com.yum.yumyums.entity.user.Member;
import lombok.Data;

@Data
public class ChatMemberDTO {

    private int id;
    private Chat chat;
    private Member member;
}
