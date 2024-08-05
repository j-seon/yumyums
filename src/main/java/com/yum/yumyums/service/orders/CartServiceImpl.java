package com.yum.yumyums.service.orders;

import com.yum.yumyums.dto.orders.CartDTO;
import com.yum.yumyums.entity.orders.Cart;
import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.repository.orders.CartRepository;
import com.yum.yumyums.repository.seller.MenuRepository;
import com.yum.yumyums.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;

    @Override
    public List<CartDTO> getCartItems(String memberId) {
        List<CartDTO> cartDTOList = new ArrayList<>();
        List<Cart> cartList = cartRepository.findByMemberId(memberId);

        for (Cart cart : cartList) {
            cartDTOList.add(cart.entityToDto());
        }
        return cartDTOList;
    }

    @Override
    public int getStoreIdFromCart(String memberId) {
        List<Cart> carts = cartRepository.findByMemberId(memberId);
        if (carts.isEmpty()) {
            return -1; // 장바구니가 비어있을 때 -1 반환
        }
        return carts.get(0).getMenu().getStore().getId();
    }

    @Override
    public boolean isDifferentStore(String memberId, int storeId) {
        int existingStoreId = getStoreIdFromCart(memberId);
        return existingStoreId != -1 && existingStoreId != storeId;
    }

    @Override
    public void addMenuToCart(CartDTO cartDTO) {
        Member member = memberRepository.findById(cartDTO.getMemberDTO().getMemberId())
                .orElseThrow(() -> new RuntimeException("멤버 없음: " + cartDTO.getMemberDTO().getMemberId()));

        Menu menu = menuRepository.findById(cartDTO.getMenuDTO().getId())
                .orElseThrow(() -> new RuntimeException("메뉴 없음: " + cartDTO.getMenuDTO().getId()));

        int menuStoreId = menu.getStore().getId();
        if (isDifferentStore(cartDTO.getMemberDTO().getMemberId(), menuStoreId)) {
            throw new IllegalStateException("같은 가게의 메뉴만 담을 수 있음");
        }

        Optional<Cart> existingCart = cartRepository.findByMemberIdAndMenuId(member.getId(), menu.getId());

        Cart cart;
        if (existingCart.isPresent()) {
            cart = existingCart.get();
            cart.setMenuCount(cart.getMenuCount() + cartDTO.getMenuCount());
        } else {
            cart = new Cart();
            cart.setMember(member);
            cart.setMenu(menu);
            cart.setMenuCount(cartDTO.getMenuCount());
        }

        cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void removeMenuFromCart(String memberId, int menuId) {
        cartRepository.removeByMemberIdAndMenuId(memberId, menuId);
    }

    @Override
    public void updateMenuCount(CartDTO cartDTO) {
        List<Cart> carts = cartRepository.findByMemberId(cartDTO.getMemberDTO().getMemberId());
        for (Cart cart : carts) {
            if (cart.getMenu().getId() == cartDTO.getMenuDTO().getId()) {
                cart.setMenuCount(cartDTO.getMenuCount());
                cartRepository.save(cart);
                return;
            }
        }
        throw new RuntimeException("카트에 메뉴 없음");
    }

    @Override
    public int getCartItemCount(String memberId) {
        List<Cart> cartList = cartRepository.findByMemberId(memberId);
        int totalItemCount = 0;
        for (Cart cart : cartList) {
            totalItemCount += cart.getMenuCount();
        }
        return totalItemCount;
    }
}
