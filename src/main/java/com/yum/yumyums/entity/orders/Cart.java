package com.yum.yumyums.entity.orders;

import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.entity.seller.Menu;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "cart")
@Getter
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

}
