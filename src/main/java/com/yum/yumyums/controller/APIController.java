package com.yum.yumyums.controller;

import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.service.user.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class APIController {
	private final SearchService searchService;

	@GetMapping("/stores")
	public List<StoreDTO> findStores(@RequestParam("searchValue") String searchValue) {
		System.out.println("searchValue: " + searchValue);
		if(searchValue == null) {
			searchValue = ""; //전체목록 가져오기
		}
		return searchService.findStores(searchValue);
	}

}
