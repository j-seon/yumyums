package com.yum.yumyums.entity.seller;

import com.yum.yumyums.entity.seller.Seller;
import com.yum.yumyums.enums.Busy;
import com.yum.yumyums.enums.FoodCategory;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "store")
@Getter
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

	@Column(name = "passwerd_hash", nullable = false)
	private String password;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 100)
	private String address;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(50)", nullable = false)
	private FoodCategory category;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Column(nullable = false)
	private int openTime;

	@Column(nullable = false)
	private int closeTime;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(50) DEFAULT 'CLEAR'", nullable = false)
	private Busy busy;


}
