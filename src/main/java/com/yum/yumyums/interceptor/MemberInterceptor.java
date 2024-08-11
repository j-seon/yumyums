package com.yum.yumyums.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.yum.yumyums.util.SessionUtil.isLoginAsMember;

@Component
public class MemberInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        HttpSession session = request.getSession();
        // 사용자 로그인 여부 확인
        if(!isLoginAsMember(session)){
            String currentUrl = request.getRequestURI();
            String msg = "&msg=member";
            response.sendRedirect("/login?redirect="+currentUrl + msg); // 로그인 페이지들 리다이렉트
            System.out.println("  MemberInterceptor 에서 처리  ");
            return false; // 요청 처리 중단
        }
        return true; // 요청 처리 계속
    }
}
