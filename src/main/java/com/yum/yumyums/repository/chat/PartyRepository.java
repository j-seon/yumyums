package com.yum.yumyums.repository.chat;

import com.yum.yumyums.entity.chat.Party;
import com.yum.yumyums.enums.PayType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartyRepository extends JpaRepository<Party, String> {
	@Query("SELECT p " +
			"FROM Party p " +
			"LEFT JOIN PartyMember pm ON p.id = pm.party.id " +
			"WHERE p.isActive = true " +
			"AND pm.member.id = :memberId")
	Party findActivePartyByMemberId(@Param("memberId")String memberId);

	@Query("SELECT p " +
			"FROM Party p " +
			"WHERE p.store.id = :storeId " +
			"AND p.isActive = true " +
			"AND p.isMatching = true " +
			"AND p.payType = :payType " +
			"AND p.maxMemberCount <= :maxMemberCount " +
			"ORDER BY p.createTime ASC")
	List<Party> findMatchingParty(
			@Param("storeId") int storeId,
			@Param("payType") PayType payType,
			@Param("maxMemberCount") int maxMemberCount,
			Pageable pageable
	);


}
