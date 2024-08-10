package com.yum.yumyums.config;

import com.yum.yumyums.interceptor.StoreInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StoreInterConfig implements WebMvcConfigurer {

    @Autowired
    private StoreInterceptor storeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        System.out.println("-------------StoreInterConfig.addInterceptor() 실행! -------------------------");
        registry.addInterceptor(storeInterceptor)
                .addPathPatterns("/dashboard/**")
                .addPathPatterns("/stores/**/menu");
    }
}
