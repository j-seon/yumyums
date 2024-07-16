package com.yum.yumyums.service.user;

import com.yum.yumyums.dto.user.MarkStationDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.user.MarkStation;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.repository.user.MarkStationRepository;
import com.yum.yumyums.repository.user.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MarkStationRepository markStationRepository;

    @Override
    @Transactional
    public void save(MemberDTO memberDTO, MarkStationDTO markStationDTO) {
        Member member = Member.toSaveEntity(memberDTO);
        Member savedMember = memberRepository.save(member);

        markStationDTO.setMember(savedMember);
        MarkStation markStation = MarkStation.toSaveEntity(markStationDTO);
        markStationRepository.save(markStation);
    }
}
