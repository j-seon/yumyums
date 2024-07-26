package com.yum.yumyums.repository.chat;

import com.yum.yumyums.entity.chat.ChatMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatMemberRepository extends JpaRepository<ChatMember, Integer> {
    List<ChatMember> findMemberSavedRoomNameByMemberId(String memberId);

    @Modifying
    @Transactional
    void deleteByChatIdAndMemberId( int chatId,  String memberId);

    Long countByChatId(int id);

    ChatMember findById(int id);

    List<ChatMember> findMemberIdByChatId(int chatId);
}
