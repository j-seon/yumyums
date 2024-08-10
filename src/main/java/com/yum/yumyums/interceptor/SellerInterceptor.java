package com.yum.yumyums.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.yum.yumyums.util.SessionUtil.isLoginAsSeller;

@Component
public class SellerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        HttpSession session = request.getSession();
        if(!isLoginAsSeller(session)){
            String currentUrl = request.getRequestURI();
            String msg = "&msg=seller";
            response.sendRedirect("/login?redirect="+currentUrl + msg);
            System.out.println("  SellerInterceptor 에서 처리  ");
            return false;
        }
        return true;
    }
}
