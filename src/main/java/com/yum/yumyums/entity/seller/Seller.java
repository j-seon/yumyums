package com.yum.yumyums.entity.seller;

import com.yum.yumyums.dto.seller.SellerDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "seller")
@Getter
@Setter
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

	public static Seller toSaveEntity(SellerDTO sellerDTO){
		Seller seller = new Seller();

		seller.setId(sellerDTO.getSellerId());
		seller.setPassword(sellerDTO.getPassword());
		seller.setSellerNum(sellerDTO.getSellerNum());
		seller.setMasterName(sellerDTO.getMasterName());
		seller.setEmail(sellerDTO.getEmail());
		seller.setPhone(sellerDTO.getPhone());

		return seller;
	}
}