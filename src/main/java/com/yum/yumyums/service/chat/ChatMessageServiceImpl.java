package com.yum.yumyums.service.chat;

import com.yum.yumyums.dto.chat.ChatMessageDTO;
import com.yum.yumyums.entity.chat.ChatMessage;
import com.yum.yumyums.repository.chat.ChatMemberRepository;
import com.yum.yumyums.repository.chat.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Override
    public void save(ChatMessageDTO chatMessageDTO) {
        chatMessageRepository.save(chatMessageDTO.dtoToEntity());
    }

    @Override
    public List<ChatMessageDTO> findByChatId(int chatId) {
        List<ChatMessageDTO> returnDtoList = new ArrayList<>();
        List<ChatMessage> returnEntityList = chatMessageRepository.findByChatId(chatId);

        for( ChatMessage returnEntity : returnEntityList){
            returnDtoList.add(returnEntity.entityToDto());
        }
        return returnDtoList;
    }
}
