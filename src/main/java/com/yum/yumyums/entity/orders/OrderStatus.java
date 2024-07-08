package com.yum.yumyums.entity.orders;

import com.yum.yumyums.enums.Busy;
import com.yum.yumyums.enums.FoodState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "order_status")
@Getter
public class OrderStatus {

	//주문한 회원ID
	@Id
	@JoinColumn(name = "order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	
	//주문상태
	@Enumerated(EnumType.STRING)
	@Column(name = "state", columnDefinition = "varchar(50)", nullable = false)
	private FoodState state;
    
}
