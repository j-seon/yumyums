package com.yum.yumyums.dto.chat;

import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.chat.Chat;
import com.yum.yumyums.entity.user.Member;
import lombok.Data;

@Data
public class ChatMemberDTO {

    private int id;
    private ChatDTO chat;
    private MemberDTO member;
    private String memberSavedRoomName;
}
