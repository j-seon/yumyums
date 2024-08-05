package com.yum.yumyums.entity.review;

import com.yum.yumyums.dto.review.ReviewDTO;
import com.yum.yumyums.entity.orders.OrdersDetail;
import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.entity.user.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "orders_detail_id", nullable = false)
    private OrdersDetail ordersDetail;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private float rate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime createTime;

    public ReviewDTO entityToDto(){
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(this.getId());
        reviewDTO.setRate(this.getRate());
        reviewDTO.setContent(this.getContent());
        reviewDTO.setStore(this.getStore().entityToDto());
        reviewDTO.setMenuDTO(this.getMenu().entityToDto());
        reviewDTO.setCreateTime(this.getCreateTime());
        return reviewDTO;
    }

}
