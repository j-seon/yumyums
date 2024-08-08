package com.yum.yumyums.entity.chat;

import com.yum.yumyums.dto.chat.PartyMemberDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.user.Member;
import jakarta.mail.Part;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartyMember {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "party_id", nullable = false)
	private Party party;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(columnDefinition = "boolean DEFAULT false", nullable = false)
	private boolean isPartyLeader = false;

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
	private LocalDateTime joinTime = LocalDateTime.now();


	public PartyMemberDTO entityToDto() {
		PartyMemberDTO partyMemberDTO = new PartyMemberDTO();
		partyMemberDTO.setId(id);
		partyMemberDTO.setPartyDTO(party.entityToDto());
		partyMemberDTO.setMemberDTO(MemberDTO.toMemberDTO(member));
		partyMemberDTO.setPartyLeader(isPartyLeader);
		partyMemberDTO.setJoinTime(joinTime);

		return partyMemberDTO;
	}

	public static PartyMember createPartyMember(Member member, Party party, boolean isPartyLeader) {
		PartyMember partyMember = new PartyMember();
		partyMember.setPartyLeader(isPartyLeader);
		partyMember.setMember(member);
		partyMember.setParty(party);
		partyMember.setJoinTime(LocalDateTime.now());

		return partyMember;
	}
}
