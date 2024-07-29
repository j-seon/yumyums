package com.yum.yumyums.repository.user;

import com.yum.yumyums.entity.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {
    List<Member> findByIdStartsWith(String memberId);
}
