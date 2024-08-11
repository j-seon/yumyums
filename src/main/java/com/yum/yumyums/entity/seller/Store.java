package com.yum.yumyums.entity.seller;


import com.yum.yumyums.dto.seller.SellerDTO;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.entity.Images;
import com.yum.yumyums.enums.Busy;
import com.yum.yumyums.enums.FoodCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


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

	@Column(nullable = false, length = 50, unique = true)
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

	@ManyToOne
	@JoinColumn(name = "images_id")
	private Images images;

	@Column(nullable = false)
	private double convX;

	@Column(nullable = false)
	private double convY;

	public StoreDTO entityToDto() {
		StoreDTO storeDTO = new StoreDTO();
		storeDTO.setStoreId(this.id);
		storeDTO.setSellerDTO(SellerDTO.toSellerDTO(this.seller));
		storeDTO.setName(this.name);
		storeDTO.setPassword(this.password);
		storeDTO.setAddress(this.address);
		storeDTO.setCategory(this.category);
		storeDTO.setContent(this.content);
		storeDTO.setOpenTime(this.openTime);
		storeDTO.setCloseTime(this.closeTime);
		storeDTO.setBusy(this.busy);
		storeDTO.setConvX(this.convX);
		storeDTO.setConvY(this.convY);
		
		// Null 체크 추가 기존 작업 중인 더미데이터에는 images 값이 없어서 부득이하게 설정
		if (this.images != null) {
			storeDTO.setImagesDTO(this.images.entityToDto());
		} else {
			storeDTO.setImagesDTO(null); // 또는 기본값을 설정할 수 있습니다.
		}

		return storeDTO;
	}

}
