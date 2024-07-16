package com.yum.yumyums.service.user;

import com.yum.yumyums.repository.user.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public boolean isValidMember(String memberId) {
		if(memberRepository.findById(memberId) == null) {
			return false;
		}
		return true;
	}
}
