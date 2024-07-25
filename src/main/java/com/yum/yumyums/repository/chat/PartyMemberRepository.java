package com.yum.yumyums.repository.chat;

import com.yum.yumyums.entity.chat.PartyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Integer> {

	List<PartyMember> findAllByPartyId(String partyId);
}
