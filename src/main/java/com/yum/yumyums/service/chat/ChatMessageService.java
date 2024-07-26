package com.yum.yumyums.service.chat;

import com.yum.yumyums.dto.chat.ChatMessageDTO;

import java.util.List;

public interface ChatMessageService {

    void save(ChatMessageDTO chatMessageDTO);

    List<ChatMessageDTO> findByChatId(int chatId);
}
