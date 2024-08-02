package com.yum.yumyums.controller;

import com.yum.yumyums.dto.ImagesDTO;
import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.seller.SellerDTO;
import com.yum.yumyums.dto.seller.StoreDTO;
import com.yum.yumyums.enums.FoodCategory;
import com.yum.yumyums.service.ImagesService;
import com.yum.yumyums.service.seller.SellerService;
import com.yum.yumyums.service.seller.StoreService;
import com.yum.yumyums.util.ImageDefaultUrl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController extends ImageDefaultUrl {
    /*
    판매자 매장 목록 - GET : /stores
    팬매자 매장 추가 - POST : /stores
    팬매자 매장 입장 - POST : /stores/login
    */

    private final StoreService storeService;
    private final SellerService sellerService;
    private final ImagesService imagesService;

    @GetMapping("")
    public String storesList(@RequestParam(defaultValue = "0") int page, Model model, TemplateData templateData, HttpServletRequest request){
        HttpSession session = request.getSession();

        if(session.getAttribute("loginType") == null || !session.getAttribute("loginType").equals("s")){
            templateData.setMessage("판매자 계정이 아닙니다.");
            templateData.setUrl("/");
            return "inc/alert";
        }

        SellerDTO sellerDto = (SellerDTO) session.getAttribute("loginUser");
        String sellerId = sellerDto.getSellerId();

        // 상점 목록과 페이지 정보 가져오기
        int pageSize = 8; // 페이지 크기 설정
        Page<StoreDTO> storePage = storeService.getStoresBySellerId(sellerId, page, pageSize);
        /*
         Page<StoreDTO> 객체에서 사용할 수 있는 주요 메서드:
         - getContent(): 현재 페이지에 해당하는 데이터 리스트를 반환합니다.
         - getTotalElements(): 전체 요소(레코드)의 개수를 반환합니다.
         - getTotalPages(): 전체 페이지 수를 반환합니다.
         - getNumber(): 현재 페이지 번호를 반환합니다 (0부터 시작).
         - getSize(): 현재 페이지의 크기(즉, 페이지당 요소 수)를 반환합니다.
         - isFirst(): 현재 페이지가 첫 페이지인지 여부를 반환합니다.
         - isLast(): 현재 페이지가 마지막 페이지인지 여부를 반환합니다.
         - hasNext(): 다음 페이지가 존재하는지 여부를 반환합니다.
         - hasPrevious(): 이전 페이지가 존재하는지 여부를 반환합니다.
         - getPageable(): Pageable 객체를 반환하여 페이지 크기와 페이지 번호 등의 정보를 제공합니다.
        * */

        System.out.println("total : "+storePage.getTotalPages());
        for(StoreDTO store : storePage.getContent()){
            System.out.println(store.toString());
        }

        // Model에 데이터 추가
        model.addAttribute("stores", storePage.getContent()); // 현재 페이지의 상점 목록
        model.addAttribute("totalPages", storePage.getTotalPages()); // 전체 페이지 수
        model.addAttribute("currentPage", storePage.getNumber()); // 현재 페이지 번호
        model.addAttribute("templateData", templateData);

        templateData.setViewPath("store/list");
        return "template";
    };

    @PostMapping("/login")
    public ResponseEntity<String> storeLogin(@RequestBody StoreDTO storeDTO, HttpServletRequest request){
        String storeName = storeDTO.getName();
        String password = storeDTO.getPassword();
        HttpSession session = request.getSession();

        StoreDTO loginStore = storeService.loginStore(storeName, password);


        System.out.println("로그인 성공? "+loginStore == null+ "="+loginStore);

        if(loginStore == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }

        session.setAttribute("storeId", loginStore.getStoreId());
        return ResponseEntity.ok("dashboard/" + loginStore.getStoreId());
    }

    @GetMapping("/form")
    public String storeSave(Model model, TemplateData templateData, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("loginType") == null || !session.getAttribute("loginType").equals("s")){
            templateData.setMessage("판매자 계정이 아닙니다.");
            templateData.setUrl("/");
            return "inc/alert";
        }

        templateData.setViewPath("store/save");

        model.addAttribute("categories", FoodCategory.values());
        model.addAttribute("templateData",templateData);
        return "template";
    }


    @PostMapping("")
    public String storeSaveSubmit(StoreDTO storeDTO, @RequestParam("storeImg") MultipartFile imgFile , HttpServletRequest request){
        HttpSession session = request.getSession();
        SellerDTO sellerDTO = (SellerDTO)session.getAttribute("loginUser");

        if(!imgFile.isEmpty()){
            imgUrl = "seller/"+sellerDTO.getSellerId()+"/"+imgFile.getOriginalFilename();
        }

        String savedImgUrl = imagesService.uploadImage(imgFile, imgUrl);

        ImagesDTO imagesDTO = new ImagesDTO();
        imagesDTO.setImgUrl(savedImgUrl);
        storeDTO.setSellerDTO(sellerDTO);
        storeDTO.setImagesDTO(imagesDTO);
        storeService.save(storeDTO);

        return "redirect:/stores";
    }

    //TODO 주소입력시 지도API 활용.
}
