package com.yum.yumyums.service.chat;

import com.yum.yumyums.dto.chat.ChatMemberDTO;
import com.yum.yumyums.entity.chat.ChatMember;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public interface ChatMemberService {

    void save(ChatMember chatMember);

    List<ChatMemberDTO> findMemberSavedRoomNameByMemberId(String number);

    void saveChatMember(ChatMember chatMember);

    void deleteByChatIdAndMemberId(int chatId, String memberId);

    List<HashMap<String, Object>> findChatRoomInfoByMemberId(String memberId);

    List<ChatMemberDTO> findMemberIdByChatId(int id);

    ChatMemberDTO findMemberIdById(int id);
}
