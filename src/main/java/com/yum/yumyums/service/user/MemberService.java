package com.yum.yumyums.service.user;

import com.yum.yumyums.dto.user.MarkStationDTO;
import com.yum.yumyums.dto.user.MemberDTO;

import java.util.List;

public interface MemberService {

    void save(MemberDTO memberDTO, MarkStationDTO markStationDTO);
    MemberDTO findById(String id);
    List<MemberDTO> findByIdStartsWith(String id);
}
