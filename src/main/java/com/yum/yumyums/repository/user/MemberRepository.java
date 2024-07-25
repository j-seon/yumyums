package com.yum.yumyums.repository.user;

import com.yum.yumyums.entity.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    List<Member> findByIdStartsWith(String memberId);
}
