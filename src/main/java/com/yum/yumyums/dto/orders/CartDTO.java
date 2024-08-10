package com.yum.yumyums.dto.orders;

import com.yum.yumyums.dto.chat.PartyDTO;
import com.yum.yumyums.dto.seller.MenuDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.orders.PartyCart;
import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.entity.user.Member;
import lombok.Data;
import org.springframework.security.core.parameters.P;

@Data
public class CartDTO {

    private int id;
    private MemberDTO memberDTO;
    private MenuDTO menuDTO;
    private int menuCount;
    private int totalPrice;

    // 파티주문 관련
    private PartyDTO partyDTO;
    private String joinPage = "none";


    public PartyCart dtoToPartyCartEntity() {
        PartyCart partyCart = new PartyCart();
        partyCart.setId(id);
        partyCart.setParty(partyDTO.dtoToEntity());
        partyCart.setMember(Member.dtoToEntity(memberDTO));
        partyCart.setMenu(Menu.dtoToEntity(menuDTO));
        partyCart.setMenuCount(menuCount);
        return partyCart;
    }
}
