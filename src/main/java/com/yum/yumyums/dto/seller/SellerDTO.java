package com.yum.yumyums.dto.seller;


import com.yum.yumyums.entity.seller.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {

    private String sellerId;
    private String password;
    private String sellerNum;
    private String masterName;
    private String email;
    private String phone;

    public static SellerDTO toSellerDTO(Seller seller){
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setSellerId(seller.getId());
        sellerDTO.setPassword(seller.getPassword());
        sellerDTO.setSellerNum(seller.getSellerNum());
        sellerDTO.setMasterName(seller.getMasterName());
        sellerDTO.setEmail(seller.getEmail());
        sellerDTO.setPhone(seller.getPhone());
        return sellerDTO;
    }
}
