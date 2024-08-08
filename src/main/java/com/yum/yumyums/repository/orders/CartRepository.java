package com.yum.yumyums.repository.orders;

import com.yum.yumyums.entity.orders.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByMemberId(String memberId);
    void removeByMemberIdAndMenuId(String memberId, int menuId);
    Optional<Cart> findByMemberIdAndMenuId(String memberId, int menuId);
    void deleteAllByMemberId(String memberId);
}
