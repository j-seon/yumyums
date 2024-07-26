package com.yum.yumyums.service.chat;

import com.yum.yumyums.entity.chat.Chat;
import com.yum.yumyums.repository.chat.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Override
    public void save(Chat chat) {
        chatRepository.save(chat);
    }
}
