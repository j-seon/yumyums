package com.yum.yumyums.repository.chat;

import com.yum.yumyums.entity.chat.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PartyRepository extends JpaRepository<Party, String> {
	@Query("SELECT p " +
			"FROM Party p " +
			"LEFT JOIN PartyMember pm ON p.id = pm.party.id " +
			"WHERE p.isActive = true " +
			"AND pm.member.id = :memberId")
	Party findActivePartyByMemberId(@Param("memberId")String memberId);

}
