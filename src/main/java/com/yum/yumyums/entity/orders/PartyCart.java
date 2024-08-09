package com.yum.yumyums.entity.orders;

import com.yum.yumyums.dto.orders.CartDTO;
import com.yum.yumyums.entity.chat.Party;
import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.entity.user.Member;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "party_cart")
@Data
public class PartyCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;


    @ColumnDefault("1")
    @Column(nullable = false)
    private int menuCount;

    public CartDTO entityToDto(){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(id);
        cartDTO.setPartyDTO(party.entityToDto());
        cartDTO.setMemberDTO(member.entityToDto());
        cartDTO.setMenuDTO(menu.entityToDto());
        cartDTO.setMenuCount(menuCount);
        return cartDTO;
    }

}
