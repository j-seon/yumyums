package com.yum.yumyums.service.chat;

import com.yum.yumyums.dto.chat.ChatMemberDTO;
import com.yum.yumyums.entity.chat.ChatMember;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.repository.chat.ChatMemberRepository;
import com.yum.yumyums.repository.user.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ChatMemberServiceImpl implements ChatMemberService {

    @Autowired
    private ChatMemberRepository chatMemberRepository;

    @Autowired
    private MemberRepository memberRepository ;

    @Override
    public void save(ChatMember chatMember) {
        chatMemberRepository.save(chatMember);
    }

    @Transactional
    public void saveChatMember(ChatMember chatMember) {
        // Member 엔티티가 저장되어 있지 않은 경우 저장합니다.
        Member member = chatMember.getMember();
        if (!memberRepository.existsById(member.getId())) {
            memberRepository.save(member);
        }

        // ChatMember 엔티티를 저장합니다.
        chatMemberRepository.save(chatMember);
    }

    @Override
    public void deleteByChatIdAndMemberId(int chatId, String memberId) {
        chatMemberRepository.deleteByChatIdAndMemberId(chatId,memberId);
    }

    @Override
    public List<HashMap<String, Object>> findChatRoomInfoByMemberId(String memberId) {

        List<HashMap<String, Object>> returnHashMapList = new ArrayList<HashMap<String, Object>>();


        List<ChatMember> returnEntities = chatMemberRepository.findMemberSavedRoomNameByMemberId(memberId);
        for (ChatMember returnEntity : returnEntities ) {
            HashMap<String, Object> returnHashMap = new HashMap<String, Object>();
            returnHashMap.put("chatInfo",returnEntity.EntityToDto());
            returnHashMap.put("chatMemberCount",chatMemberRepository.countByChatId(returnEntity.EntityToDto().getChat().getId()));
            returnHashMapList.add(returnHashMap);
        }

        return returnHashMapList;
    }

    @Override
    public List<ChatMemberDTO> findMemberSavedRoomNameByMemberId(String memberId) {
        List<ChatMemberDTO> returnDto =  new ArrayList<>();

        List<ChatMember> returnEntities = chatMemberRepository.findMemberSavedRoomNameByMemberId(memberId);


        for (ChatMember returnEntity : returnEntities ) {
            returnDto.add(returnEntity.EntityToDto());
        }
        return returnDto;
    }
}
