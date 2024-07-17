package com.yum.yumyums.entity.chat;

import com.yum.yumyums.entity.user.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class PartyMember {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@Column(name = "party_id", nullable = false)
	private Party party;

	@ManyToOne
	@Column(name = "member_id", nullable = false)
	private Member member;

	@Column(columnDefinition = "boolean DEFAULT false", nullable = false)
	private boolean isPartyLeader = false;

}
