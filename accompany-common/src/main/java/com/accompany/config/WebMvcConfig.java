package com.accompany.config;

import com.accompany.intercepter.UserIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private UserIntercepter userIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userIntercepter)
                .addPathPatterns("/**") // 拦截所有路径
                .excludePathPatterns(
                        "/auth/login",
                        "/auth/register",
                        "/auth/logout"
                );
    }
}
