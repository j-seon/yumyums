package com.yum.yumyums.entity.seller;

import com.yum.yumyums.dto.seller.StoreLikeDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.user.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "store_like")
@Getter
@Setter
public class StoreLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


    public StoreLikeDTO entityToDto() {
        StoreLikeDTO storeLikeDTO = new StoreLikeDTO();
        storeLikeDTO.setStoreDTO(this.store.entityToDto());
        storeLikeDTO.setMemberDTO(MemberDTO.toMemberDTO(this.member));
        return storeLikeDTO;
    }

}
