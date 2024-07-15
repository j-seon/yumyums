package com.yum.yumyums.service.user;

import com.yum.yumyums.dto.user.MarkStationDTO;
import com.yum.yumyums.dto.user.MemberDTO;

public interface MemberService {

    void save(MemberDTO memberDTO, MarkStationDTO markStationDTO);
    MemberDTO findById(String id);
}
