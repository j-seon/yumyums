package com.yum.yumyums.entity.seller;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "seller")
@Getter
public class Seller {

    @Id
    @Column(length = 50)
    private String id;

	@Column(name = "password_hash", nullable = false, length = 100)
	private String password;

	@Column(nullable = false, length = 100)
	private String sellerNum;

	@Column(nullable = false, length = 50)
	private String masterName;

	@Column(length = 100)
	private String email;

	@Column(length = 100)
	private String phone;
}