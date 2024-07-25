package com.yum.yumyums.repository.user;

import com.yum.yumyums.entity.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

}
