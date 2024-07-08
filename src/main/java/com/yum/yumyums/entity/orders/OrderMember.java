package com.yum.yumyums.entity.orders;

import com.yum.yumyums.entity.user.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "order_member")
@Getter
public class OrderMember {

	//주문한 회원ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	//회원ID
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    
    //주문ID
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
