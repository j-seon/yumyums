package com.yum.yumyums.entity.orders;

import com.yum.yumyums.dto.orders.OrdersStatusDTO;
import com.yum.yumyums.enums.FoodState;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "orders_status")
@Data
public class OrdersStatus {

	@Id
	private String id;

	// 주문 ID
	@OneToOne
	@MapsId
	@JoinColumn(name = "orders_id", nullable = false)
	private Orders orders;

	// 주문 상태
	@Enumerated(EnumType.STRING)
	@Column(name = "state", columnDefinition = "varchar(50) DEFAULT 'COOKING'", nullable = false)
	private FoodState state;

	public OrdersStatusDTO entityToDto(){
		OrdersStatusDTO ordersStatusDTO = new OrdersStatusDTO();
		ordersStatusDTO.setState(this.state);
		return ordersStatusDTO;
	}
}