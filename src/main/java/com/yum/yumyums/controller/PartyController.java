package com.yum.yumyums.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/party")
public class PartyController {
	//TODO 파티초대를 실행시, 파티초대 링크를 전달해줌

	@PutMapping
	public void createParty() {

	}



	//TODO
	//TODO 로그인 상태로 파티초대 링크에 접근할 경우, Service의 파티초대 실행
	//TODO 로그인이 아닌 상태로 파티초대 링크에 접근한 경우, Login 페이지로 이동

}
