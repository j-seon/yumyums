package com.yum.yumyums.controller;

import com.yum.yumyums.entity.seller.Store;
import com.yum.yumyums.service.user.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {
	@Autowired
	SearchService searchService;

	@GetMapping
	public String findStores(Model model, String searchValue) {
		if(searchValue == null) {
			searchValue = ""; //전체목록 가져오기
		}
		List<Store> stores = searchService.findStores(searchValue);
		model.addAttribute("stores", stores);
		model.addAttribute("review", "4.7"); //TODO 가게 평점을 어딘가에서 받아와야 함.

		return "party/search_store";
	}


}
