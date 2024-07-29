package com.yum.yumyums.dto.chat;

import com.yum.yumyums.entity.chat.Chat;
import lombok.Data;

@Data
public class ChatDTO {

    private int id;
    private String name;

    public Chat dtoToEntity() {
        Chat chat = new Chat();
        chat.setId(this.getId());
        chat.setName(this.getName());
        return  chat;
    }
}


