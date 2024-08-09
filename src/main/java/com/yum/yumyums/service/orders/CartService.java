package com.yum.yumyums.service.orders;

import com.yum.yumyums.dto.orders.CartDTO;
import java.util.List;

public interface CartService {
    List<CartDTO> getCartItems(String memberId);
    int getStoreIdFromCart(String memberId);
    boolean isDifferentStore(String memberId, int storeId);
    void addMenuToCart(CartDTO cartDTO);
    void removeMenuFromCart(String memberId, int menuId);
    void updateMenuCount(CartDTO cartDTO);
    int getCartItemCount(String memberId);

    //[Party] 파티 장바구니에 추가
    void addMenuToPartyCart(CartDTO cartDTO);
}
