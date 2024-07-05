package com.yum.yumyums.entity.chat;

import com.yum.yumyums.entity.seller.PayType;
import com.yum.yumyums.entity.seller.Store;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Party {

	@Id
	@Column(length = 50)
	private String id;

	@ManyToOne
	@JoinColumn(name = "chat_id")
	private Chat chat;

	@ManyToOne
	@JoinColumn(name = "shore_id")
	private Store store;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(50)", nullable = false)
	private PayType payType;
}
