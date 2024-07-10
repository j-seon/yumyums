package com.yum.yumyums.repository.chat;

import com.yum.yumyums.entity.chat.ChatMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMemberRepository extends JpaRepository<ChatMember, Integer> {
}
