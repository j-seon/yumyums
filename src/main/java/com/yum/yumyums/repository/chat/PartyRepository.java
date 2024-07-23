package com.yum.yumyums.repository.chat;

import com.yum.yumyums.entity.chat.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party, String> {

}
