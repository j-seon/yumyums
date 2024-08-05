package com.yum.yumyums.dto.chat;

import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.enums.PayType;
import lombok.Data;

@Data
public class MatchRequestDTO {
	private MemberDTO memberDTO;
	private String storeName;
	private PayType payType;
	private int maxMemberCount;
}
