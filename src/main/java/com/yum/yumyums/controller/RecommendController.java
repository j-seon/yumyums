package com.yum.yumyums.controller;

import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.seller.MenuDTO;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.enums.FoodCategory;
import com.yum.yumyums.service.seller.MenuService;
import com.yum.yumyums.service.seller.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.*;

import static com.yum.yumyums.util.SessionUtil.MEMBER_DTO_SESSION_ATTRIBUTE_NAME;

@Controller
@RequiredArgsConstructor
@RequestMapping("/menu")
public class RecommendController {
    private final MenuService menuService;
    private final StoreService storeService;
    @GetMapping
    public String getMenusByFilters(
            @RequestParam(required = false) List<FoodCategory> categories,
            @RequestParam(required = false) List<String> priceRanges,
            @RequestParam(required = false) Boolean isAlone,
            @RequestParam(required = false) String sort,
            Model model, TemplateData templateData
    ) {
        templateData.setViewPath("menu/list");

        // 필터링된 메뉴 목록 가져오기
        List<MenuDTO> menus = menuService.getMenusByFilters(categories, priceRanges, isAlone, sort);

        // 좋아요 수 및 평점, 주문건수 관련 데이터 준비
        Map<Integer, Integer> likeCounts = new HashMap<>();
        Map<Integer, Double> averageRatings = new HashMap<>();
        Map<Integer, Integer> orderCounts = new HashMap<>();

        for (MenuDTO menu : menus) {
            int storeId = menu.getStoreDTO().getStoreId();
            int menuId = menu.getId();

            // 좋아요 수 및 평점 계산
            int likeCount = storeService.getLikesForStore(storeId);
            double averageRating = menuService.getAverageRateForMenu(menuId);

            // 주문 횟수 계산
            int orderCount = menuService.getMenuOrderCount(menuId);

            likeCounts.put(storeId, likeCount);
            averageRatings.put(menuId, averageRating);
            orderCounts.put(menuId, orderCount);
        }

        // 모델에 데이터 추가
        model.addAttribute("menus", menus);
        model.addAttribute("likeCounts", likeCounts);
        model.addAttribute("averageRatings", averageRatings);
        model.addAttribute("orderCounts", orderCounts);
        model.addAttribute("templateData", templateData);

        return "template";
    }



    @GetMapping("/{storeId}")
    public String getStoreDetails(
			@PathVariable("storeId") int storeId,
			Model model,
			TemplateData templateData,
			@RequestParam(name = "joinPage", required = false, defaultValue = "none") String joinPage,
			@RequestParam(name = "partyId", required = false, defaultValue = "none") String encryptedPartyId,
            HttpServletRequest request
	) {

        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO)session.getAttribute(MEMBER_DTO_SESSION_ATTRIBUTE_NAME);
        String memberId = memberDTO.getMemberId();

        // 클라이언트가 단골로 등록한 식당인지 확인하기
        boolean isStoreLike = storeService.isStoreLikedByMember(memberId, storeId);
        model.addAttribute("isStoreLike", isStoreLike);

        templateData.setViewPath("menu/detail");
        StoreDTO store = storeService.findById(storeId);
        model.addAttribute("store", store);

        List<MenuDTO> menuList = menuService.getMenusByStoreId(storeId);
        model.addAttribute("menulist", menuList);

        Map<Integer, Double> averageRate = new HashMap<>();
        for (MenuDTO menu : menuList) {
            double averageRating = menuService.getAverageRateForMenu(menu.getId());
            averageRate.put(menu.getId(), averageRating);
        }
        model.addAttribute("averageRate", averageRate);

        int likeCount = storeService.getLikesForStore(storeId);
        model.addAttribute("likeCount", likeCount);

		// 연관 페이지 이동을 위한 기본값 실어 보내기
		model.addAttribute("partyId", "none");
		model.addAttribute("joinPage", "none");

		// 파티페이지에서 접근했다면
		if(joinPage.equals("party")) {
			//연관 페이지 정보값을 파티로 변경
			model.addAttribute("partyId", encryptedPartyId);
			model.addAttribute("joinPage", joinPage);
		}

		model.addAttribute("templateData", templateData);


        return "template";
    }
}
