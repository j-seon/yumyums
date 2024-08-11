package com.yum.yumyums.repository.orders;

import com.yum.yumyums.entity.orders.PartyCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PartyCartRepository extends JpaRepository<PartyCart, Integer> {
	Optional<PartyCart> findByMemberIdAndMenuId(String memberId, int menuId);


    List<PartyCart> findByMemberId(String memberId);

    void deleteAllByMemberIdAndPartyId(String memberId, String partyId);

}
