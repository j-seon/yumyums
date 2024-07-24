package com.yum.yumyums.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class MemberJoinRequest {
    private MemberDTO memberDTO;
    private List<MarkStationDTO> markStationDTOs;
}
