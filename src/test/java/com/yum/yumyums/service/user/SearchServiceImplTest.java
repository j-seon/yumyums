package com.yum.yumyums.service.user;

import com.yum.yumyums.entity.seller.Menu;
import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.enums.FoodCategory;
import com.yum.yumyums.repository.user.SearchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class SearchServiceImplTest {
	@Mock
	private SearchRepository searchRepository;

	@InjectMocks
	private SearchServiceImpl storeService;

	@Test
	@DisplayName("검색어가 들어간 메뉴가 모두 리스트로 저장돼야합니다")
	void findMenus() {
		String searchValue = "라면";

		Store store1 = new Store();
		store1.setId(1);
		store1.setName("시아");
		store1.setCategory(FoodCategory.ETC);

		Menu menu1 = new Menu();
		menu1.setId(1);
		menu1.setName("떡라면");
		menu1.setStore(store1);

		Menu menu2 = new Menu();
		menu2.setId(2);
		menu2.setName("진심 가득 라면");
		menu1.setStore(store1);

		Menu menu3 = new Menu();
		menu3.setId(3);
		menu3.setName("삼계탕");
		menu1.setStore(store1);

		when(searchRepository.findByNameLike(searchValue)).thenReturn(Arrays.asList(menu1, menu2));

		// when
		List<Menu> result = storeService.findMenus(searchValue);

		// then
		assertThat(result).hasSize(2);
		assertThat(result).extracting(Menu::getName).containsExactlyInAnyOrder("떡라면", "진심 가득 라면");

	}
	@Test
	@DisplayName("검색어가 메뉴명에 포함될시 메뉴를 가진 Store 객체를 반환해야 합니다.")
	public void findStoreMenu() throws Exception {
		String searchValue = "치킨";

		Store store1 = new Store();
		store1.setId(1);
		store1.setName("시아");
		store1.setCategory(FoodCategory.ETC);

		Menu menu1 = new Menu();
		menu1.setId(1);
		menu1.setName("쏘 치킨");
		menu1.setStore(store1);

		when(searchRepository.findStores(searchValue)).thenReturn(Arrays.asList(store1));

		// when
		List<Store> result = storeService.findStores(searchValue);

		// then
		assertThat(result).hasSize(1);
		assertThat(result).extracting(Store::getName).containsExactlyInAnyOrder("시아");

	}

	@Test
	@DisplayName("검색어가 상점명에 포함될시 해당 상점명의 Store 객체를 반환해야 합니다.")
	public void findStoreName() throws Exception {
		String searchValue = "최고";

		Store store1 = new Store();
		store1.setId(1);
		store1.setName("최고의 치킨");
		store1.setCategory(FoodCategory.ETC);

		Menu menu1 = new Menu();
		menu1.setId(1);
		menu1.setName("매콤 치킨");
		menu1.setStore(store1);

		when(searchRepository.findStores(searchValue)).thenReturn(Arrays.asList(store1));

		// when
		List<Store> result = storeService.findStores(searchValue);

		// then
		assertThat(result).hasSize(1);
		assertThat(result).extracting(Store::getName).containsExactlyInAnyOrder("최고의 치킨");

	}

	@Test
	@DisplayName("한 상점이 중복적으로 나오지 않아야합니다")
	public void storeDuplicateExamination() throws Exception {
		String searchValue = "최고";

		Store store1 = new Store();
		store1.setId(1);
		store1.setName("최고의 치킨");
		store1.setCategory(FoodCategory.ETC);

		Menu menu1 = new Menu();
		menu1.setId(1);
		menu1.setName("매콤 치킨");
		menu1.setStore(store1);

		Menu menu2 = new Menu();
		menu2.setId(2);
		menu2.setName("달콤 치킨");
		menu2.setStore(store1);

		when(searchRepository.findStores(searchValue)).thenReturn(Arrays.asList(store1));

		// when
		List<Store> result = storeService.findStores(searchValue);

		// then
		assertThat(result).hasSize(1);
		assertThat(result).extracting(Store::getName).containsExactlyInAnyOrder("최고의 치킨");

	}

}