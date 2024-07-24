package com.yum.yumyums.service.user;

import com.yum.yumyums.dto.user.MarkStationDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.dto.user.MemberJoinRequest;

public interface MemberService {

    void save(MemberJoinRequest memberJoinRequest);
    void saveMember(MemberDTO memberDTO);
    MemberDTO findById(String id);
}
