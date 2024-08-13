package com.yum.yumyums.config;

import com.yum.yumyums.interceptor.MemberInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MemberInterConfig implements WebMvcConfigurer {

    @Autowired
    private MemberInterceptor memberInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        System.out.println("-------------MemberInterceptor.addInterceptor() 실행! -------------------------");
        registry.addInterceptor(memberInterceptor)
                .addPathPatterns("/member/**")
                .addPathPatterns("/cart")
                .addPathPatterns("/maps/**")
                .addPathPatterns("/menu")
                .addPathPatterns("/menu/*")
                .addPathPatterns("/orders/**")
                /*.addPathPatterns("/chat/**")*/
//                .addPathPatterns("/party")
                .excludePathPatterns("/member")
                .excludePathPatterns("/member/login");
    }
}
