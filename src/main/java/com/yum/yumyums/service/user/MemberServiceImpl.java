package com.yum.yumyums.service.user;

import com.yum.yumyums.dto.user.MarkStationDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.user.MarkStation;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.repository.user.MarkStationRepository;
import com.yum.yumyums.repository.user.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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

    @Override
    public MemberDTO findById(String id) {
        Optional<Member> optionalMember = memberRepository.findById(id);

        if(optionalMember.isPresent()){
            Member member = optionalMember.get();
            MemberDTO memberDTO = MemberDTO.toMemberDTO(member);
            return memberDTO;
        } else{
            return null;
        }
    }

	@Override
	public boolean isValidMember(String memberId) {
		if(memberRepository.findById(memberId) == null) {
			return false;
		}
		return true;
	}
}
