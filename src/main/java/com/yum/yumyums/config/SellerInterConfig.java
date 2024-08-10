package com.yum.yumyums.config;

import com.yum.yumyums.interceptor.SellerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SellerInterConfig implements WebMvcConfigurer {

    @Autowired
    private SellerInterceptor sellerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        System.out.println("-------------SellerInterConfig.addInterceptor() 실행! -------------------------");
        registry.addInterceptor(sellerInterceptor)
                .addPathPatterns("/seller/**")
                .addPathPatterns("/stores")
                .addPathPatterns("/stores/*")
                .excludePathPatterns("/seller")
                .excludePathPatterns("/seller/login")
                .excludePathPatterns("/stores/login");
    }
}
