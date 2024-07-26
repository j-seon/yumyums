package com.yum.yumyums.repository.chat;

import com.yum.yumyums.entity.chat.PartyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Integer> {

	List<PartyMember> findAllByPartyId(String partyId);

	//SELECT COUNT(*) > 0 FROM party p LEFT JOIN party_member pm ON p.id = pm.party_id
	//WHERE p.is_active = true AND p.id = ?1 AND pm.member_id = ?2;
	boolean existsByPartyIdAndMemberIdAndPartyIsActiveTrue(String partyId, String memberId);
}
