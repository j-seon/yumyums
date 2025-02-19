package com.yum.yumyums.service.user;

import com.yum.yumyums.dto.user.MarkStationDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.dto.user.MemberJoinRequest;
import com.yum.yumyums.entity.Images;
import com.yum.yumyums.entity.user.MarkStation;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.repository.user.MarkStationRepository;
import com.yum.yumyums.repository.user.MemberRepository;
import com.yum.yumyums.service.ImagesService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MarkStationRepository markStationRepository;

    private final EntityManager entityManager;
    private final ImagesService imagesService;

    @Override
    @Transactional
    public void save(MemberJoinRequest memberJoinRequest) {
       /* Member member = Member.dtoToEntity(memberDTO);
        Member savedMember = memberRepository.save(member);

        markStationDTO.setMember(savedMember);
        MarkStation markStation = MarkStation.toSaveEntity(markStationDTO);
        markStationRepository.save(markStation);*/
        MemberDTO memberDTO = memberJoinRequest.getMemberDTO();
        Images savedImages = imagesService.saveImage(memberDTO.getImagesDTO());
        
        Member member = Member.dtoToEntity(memberDTO);
        member.setImages(savedImages);
        System.out.println("member : "+ member);
        memberRepository.save(member);

        List<MarkStationDTO> markStationDTOList = memberJoinRequest.getMarkStationDTOs();
        if(markStationDTOList != null){
            for(MarkStationDTO stationDTO : markStationDTOList){
                if(stationDTO.getStationId() != null){

                    stationDTO.setMemberId(memberDTO.getMemberId());
                    MarkStation markStation = MarkStationDTO.dtoToEntity(stationDTO, entityManager);
                    System.out.println("station : "
                            +", "+ markStation.getClass()
                            +", "+ markStation.getId()
                            +", "+ markStation.getMember().getId()
                            +", "+ markStation.getStation().getId()
                            +", "+ markStation.getMemo()
                    );
                    markStationRepository.save(markStation);
                }

            }
        }


    }

    @Override
    @Transactional
    public void saveMember(MemberDTO memberDTO) {
        Member member = Member.dtoToEntity(memberDTO);
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
    @Override
    public List<MemberDTO> findByIdStartsWith(String id) {
        List<Member> optionalMember = memberRepository.findByIdStartsWith(id);
        List<MemberDTO> returnDto=new ArrayList<>();
        for (Member findEntity :  optionalMember) {
            returnDto.add(MemberDTO.toMemberDTO(findEntity));
        }
        return returnDto;
    }

}
