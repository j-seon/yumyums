package com.yum.yumyums.entity.orders;

import com.yum.yumyums.dto.orders.CartDTO;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.entity.seller.Menu;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "cart")
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
        cartDTO.setId(this.getId());
        cartDTO.setMemberDTO(this.member.entityToDto());
        cartDTO.setMenuDTO(this.menu.entityToDto());
        cartDTO.setMenuCount(this.getMenuCount());
        return cartDTO;
    }

    public static Cart dtoToEntity(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setId(cartDTO.getId());
        cart.setMember(Member.dtoToEntity(cartDTO.getMemberDTO()));
        cart.setMenu(Menu.dtoToEntity(cartDTO.getMenuDTO()));
        cart.setMenuCount(cartDTO.getMenuCount());
        return cart;
    }

}
