package com.yum.yumyums.entity.seller;

import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.enums.Busy;
import com.yum.yumyums.enums.FoodCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "store")
@Getter @Setter
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

	@Column(name = "password_hash", nullable = false)
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
	@Column(columnDefinition = "varchar(50) DEFAULT 'SPACIOUS'", nullable = false)
	private Busy busy;

	public StoreDTO entityToDTO() {
		StoreDTO storeDTO = new StoreDTO();
		storeDTO.setId(this.getId());
		storeDTO.setName(this.getName());
		storeDTO.setAddress(this.getAddress());
		storeDTO.setCategory(this.getCategory());
		storeDTO.setContent(this.getContent());
		storeDTO.setOpenTime(this.getOpenTime());
		storeDTO.setCloseTime(this.getCloseTime());
		storeDTO.setBusy(this.getBusy());

		return storeDTO;
	}
}
