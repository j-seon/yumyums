package com.yum.yumyums.entity.chat;

import com.yum.yumyums.enums.PayType;
import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.enums.RandomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Party {
	@Id
	@Column(length = 50)
	private String id;

	@ManyToOne
	@JoinColumn(name = "shore_id")
	private Store store;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(50)", nullable = false)
	private PayType payType;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(50)", nullable = false)
	private RandomType randomType;

	@Column(columnDefinition = "boolean DEFAULT true", nullable = false)
	private boolean isActive = true;
}
