package com.yum.yumyums.service.seller;

import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.dto.seller.StoreLikeDTO;
import com.yum.yumyums.entity.Images;
import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.entity.seller.StoreLike;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.repository.seller.StoreRepository;
import com.yum.yumyums.repository.user.MemberRepository;
import com.yum.yumyums.service.ImagesService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.yum.yumyums.repository.seller.StoreLikeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final ImagesService imagesService;
    private final StoreLikeRepository storeLikeRepository;
    private final MemberRepository memberRepository;

	@Override
	public StoreDTO findByName(String storeName) {
        // storeName이 null이거나 빈 문자열일 경우 예외 처리
        if (storeName == null || storeName.trim().isEmpty()) {
            return null; // 또는 예외를 던질 수 있음
        }

		Store store = storeRepository.findByName(storeName);

        // Store이 null일 경우 null 반환
        if (store == null) {
            return null;
        }
		return store.entityToDto();
	}

    @Override
    public Page<StoreDTO> getStoresBySellerId(String sellerId, int page, int pageSize) {
        Page<Store> storePage = storeRepository.findBySellerId(sellerId, PageRequest.of(page, pageSize));

		// Store 엔티티를 StoreDTO로 변환
		List<StoreDTO> storeDTOs = storePage.getContent().stream()
            .map(store -> {
                StoreDTO dto = store.entityToDto(); // 인스턴스 메서드 호출
                int likes = getLikesForStore(store.getId()); // 좋아요 수 조회
                dto.setLikes(likes); // 좋아요 수 설정
                return dto;
            })
            .collect(Collectors.toList());

		// Page<StoreDTO>로 변환하여 반환
		return new PageImpl<>(storeDTOs, storePage.getPageable(), storePage.getTotalElements());
		/*
		이 코드는 Spring Data JPA에서 제공하는 Page 인터페이스의 구현체인 PageImpl 객체를 생성하여 반환하는 부분입니다.
		아래에서 이 코드의 각 요소에 대해 자세히 설명하겠습니다.

			1. PageImpl 클래스
			PageImpl 클래스는 Spring Data에서 제공하는 기본적인 Page 구현체입니다.
			이 클래스는 페이지화된 데이터를 다루기 위해 사용되며, 다음과 같은 기능을 제공합니다:
			 - 현재 페이지의 데이터 목록
			 - 전체 데이터의 개수
			 - 페이지 크기 및 현재 페이지 번호

			2. 생성자 인자 설명
			PageImpl 생성자는 다음과 같은 파라미터를 받습니다:
			 - 첫 번째 인자 (storeDTOs): 현재 페이지에 해당하는 데이터 목록입니다. 여기서는 List<StoreDTO>로 변환된 상점 DTO 리스트를 전달합니다.
			 - 두 번째 인자 (storePage.getPageable()): 페이지 정보입니다. storePage.getPageable()은 현재 페이지의 크기, 페이지 번호 등의 정보를 포함하는 Pageable 객체를 반환합니다. 이 정보를 사용하여 클라이언트가 요청한 페이지에 대한 메타데이터를 제공합니다.
			 - 세 번째 인자 (storePage.getTotalElements()): 전체 데이터의 개수입니다. storePage.getTotalElements()는 원본 Page<Store>에서 전체 데이터 수를 가져옵니다. 이를 통해 클라이언트는 전체 페이지 수를 계산할 수 있습니다.

			3. 전체 흐름
			이 코드의 흐름은 다음과 같습니다:
			 - 1. DTO 변환: Page<Store>에서 가져온 List<Store>를 List<StoreDTO>로 변환합니다.
			 - 2. 페이지 정보 유지: 변환된 DTO 리스트와 함께 원본 페이지 정보(현재 페이지 정보와 전체 데이터 수)를 유지합니다.
			 - 3. PageImpl 생성: PageImpl 객체를 생성하여, 클라이언트에게 필요한 페이지화된 데이터를 제공합니다.
		* */
    }

	@Override
	public StoreDTO loginStore(String storeName, String password) {
		Store store = storeRepository.findByName(storeName);

		if(password.equals(store.getPassword())){
			return store.entityToDto();
		}
		return null;
	}

    @Override
    public int getLikesForStore(int storeId) {
        return storeLikeRepository.countLikesByStoreId(storeId);
    }

    @Override
    public List<StoreDTO> getStoresOnMap() {
        List<StoreDTO> store = new ArrayList<>();
        List<Store> findAll = storeRepository.findAll();
        for (Store s : findAll) {
            store.add(s.entityToDto());
        }
        return store;
    }

    @Override
    @Transactional
    public void save(StoreDTO storeDTO) {
        Images savedImages = imagesService.saveImage(storeDTO.getImagesDTO());

        Store store = storeDTO.dtoToEntity();
        store.setImages(savedImages);
        storeRepository.save(store);
    }

    @Override
    public StoreDTO findById(int storeId) {
        Optional<Store> store = storeRepository.findById(storeId);
        return store.get().entityToDto();
    }

    public List<StoreDTO> findStoresWithinRadius(double lat, double lon, int radius) {
        List<Store> stores = storeRepository.findStoresWithinRadius(lat, lon, radius);
        List<StoreDTO> result = new ArrayList<>();

        for (Store store : stores) {
            StoreDTO dto = store.entityToDto();
            result.add(dto);
            System.out.println("store : "+ store);
        }
        return result;
    }

    @Override
    public void update(Store store) {
        System.out.println("update store : "+store.entityToDto());
        storeRepository.save(store);
    }

    @Override
    public Page<StoreLikeDTO> getStoreLikesByMemberId(String memberId, int page, int pageSize) {
        Page<StoreLike> storeLikesPage = storeLikeRepository.findByMemberId(memberId, PageRequest.of(page, pageSize));

        List<StoreLikeDTO> storeLikeDTOs = storeLikesPage.getContent().stream()
                .map(storeLike -> {
                    StoreLikeDTO storeLikeDTO = storeLike.entityToDto();
                    StoreDTO storeDTO = storeLikeDTO.getStoreDTO();
                    int likes = getLikesForStore(storeDTO.getStoreId());
                    storeDTO.setLikes(likes);
                    storeLikeDTO.setStoreDTO(storeDTO);
                    return storeLikeDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(storeLikeDTOs, storeLikesPage.getPageable(), storeLikesPage.getTotalElements());
    }

    @Override
    public boolean isStoreLikedByMember(String memberId, int storeId) {
            return storeLikeRepository.existsByMemberIdAndStoreId(memberId, storeId);
    }

    @Transactional
    @Override
    public void saveStoreLike(String memberId, int storeId) {
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Store> store = storeRepository.findById(storeId);

        // 멤버와 매장이 존재하는지 확인
        if (!member.isPresent()) {
            throw new EntityNotFoundException("Member not found with id: " + memberId);
        }

        if (!store.isPresent()) {
            throw new EntityNotFoundException("Store not found with id: " + storeId);
        }

        // StoreLike 객체 생성 및 설정
        StoreLike storeLike = new StoreLike();
        storeLike.setStore(store.get());
        storeLike.setMember(member.get());

        // 단골 등록 정보 저장
        storeLikeRepository.save(storeLike);
    }

    @Override
    public void removeStoreLike(String memberId, int storeId) {
        // 해당 멤버와 매장에 해당하는 단골 정보를 찾아서 삭제
        StoreLike storeLike = storeLikeRepository.findByMemberIdAndStoreId(memberId, storeId);
        if (storeLike != null) {
            storeLikeRepository.delete(storeLike);
        } else {
            throw new EntityNotFoundException("단골 정보가 존재하지 않습니다.");
        }
    }

}
