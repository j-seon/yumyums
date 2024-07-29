package com.yum.yumyums.service.seller;

import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.repository.seller.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.yum.yumyums.repository.seller.StoreLikeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreLikeRepository storeLikeRepository;

    private final StoreRepository storeRepository;

	@Override
	public StoreDTO findByName(String storeName) {
		Store store = storeRepository.findByName(storeName);
		return store.entityToDto();
	}

    @Override
    public Page<StoreDTO> getStoresBySellerId(String sellerId, int page, int pageSize) {
        Page<Store> storePage = storeRepository.findBySellerId(sellerId, PageRequest.of(page, pageSize));

		// Store 엔티티를 StoreDTO로 변환
		List<StoreDTO> storeDTOs = storePage.getContent().stream()
				.map(Store::entityToDto) // 변환 메서드 호출
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

    public int getLikesForStore(int storeId) {
        return storeLikeRepository.countLikesByStoreId(storeId);
    }

    public List<StoreDTO> getStoresOnMap() {
        List<StoreDTO> store = new ArrayList<>();
        List<Store> findAll = storeRepository.findAll();
        for (Store s : findAll) {
            store.add(s.entityToDto());
        }
        return store;
    }

}
