package com.yum.yumyums.repository.chat;

import com.yum.yumyums.entity.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
}
