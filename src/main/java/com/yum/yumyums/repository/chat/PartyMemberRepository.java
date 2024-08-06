package com.yum.yumyums.repository.chat;

import com.yum.yumyums.entity.chat.PartyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Integer> {

	List<PartyMember> findAllByPartyId(String partyId);

	//SELECT COUNT(*) > 0 FROM party p LEFT JOIN party_member pm ON p.id = pm.party_id
	//WHERE p.is_active = true AND p.id = ?1 AND pm.member_id = ?2;
	boolean existsByPartyIdAndMemberIdAndPartyIsActiveTrue(String partyId, String memberId);

	@Query("SELECT COUNT(pm) > 0 " +
			"FROM Party p " +
			"LEFT JOIN PartyMember pm ON p.id = pm.party.id " +
			"WHERE p.isActive = true " +
			"AND p.id = :partyId " +
			"AND pm.member.id = :memberId " +
			"AND pm.isPartyLeader = true")
	boolean existsActivePartyWithLeader(@Param("partyId") String partyId, @Param("memberId") String memberId);

	void deleteByMemberId(String MemberId);

	int countByPartyId(String partyId);

	PartyMember findByPartyIdAndMemberId(String partyId, String memberId);
}
