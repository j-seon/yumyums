package com.yum.yumyums.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.yum.yumyums.util.SessionUtil.isLoginAsStore;

@Component
public class StoreInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        HttpSession session = request.getSession();
        if(!isLoginAsStore(session)){
            // Referer 헤더에서 이전 URL 가져오기
            String refererUrl = request.getHeader("Referer");

            if (refererUrl != null) {
                response.sendRedirect(refererUrl); // 이전 URL로 리다이렉트
            } else {
                response.sendRedirect("/stores"); // Referer가 없을 경우 기본 페이지로 리다이렉트
            }
            System.out.println("  StoreInterceptor 에서 처리  ");
            return false;
        }
        return true;
    }
}
