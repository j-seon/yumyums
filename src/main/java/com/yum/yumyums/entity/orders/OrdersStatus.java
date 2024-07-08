package com.yum.yumyums.entity.orders;

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
@Table(name = "orders_status")
@Getter
public class OrdersStatus {

	//주문한 회원ID
	@Id
	@JoinColumn(name = "orders_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ordersId;
	
	//주문상태
	@Enumerated(EnumType.STRING)
	@Column(name = "state", columnDefinition = "varchar(50)", nullable = false)
	private FoodState state;
    
}
