package com.yum.yumyums.entity.orders;

import com.yum.yumyums.enums.FoodState;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "orders_status")
@Getter
public class OrdersStatus {

	// 주문 ID
	@Id
	@Column(name = "orders_id")
	private String ordersId;

	// 주문 상태
	@Enumerated(EnumType.STRING)
	@Column(name = "state", columnDefinition = "varchar(50)", nullable = false)
	private FoodState state;
}