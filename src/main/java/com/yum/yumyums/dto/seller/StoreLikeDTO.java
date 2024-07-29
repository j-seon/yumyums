package com.yum.yumyums.dto.seller;

import com.yum.yumyums.dto.user.MemberDTO;
import lombok.Data;

@Data
public class StoreLikeDTO {
    private int id;
    private MemberDTO memberDTO;
    private StoreDTO storeDTO;

}
