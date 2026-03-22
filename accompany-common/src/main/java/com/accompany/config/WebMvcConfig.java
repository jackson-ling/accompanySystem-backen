package com.accompany.config;

import com.accompany.intercepter.UserIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private UserIntercepter userIntercepter;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userIntercepter)
                .addPathPatterns("/**") // 拦截所有路径
                .excludePathPatterns(
                        // 认证相关接口（公开访问）
                        "/auth/**",
                        // 服务相关接口（公开访问）
                        "/services/**",
                        // 陪诊师浏览相关接口（公开访问，但不包括收藏等需要登录的功能）
                        "/companions", // GET 陪诊师列表
                        "/companions/*/comments", // GET 陪诊师评价
                        "/companions/*/available-times", // GET 陪诊师可用时间
                        // 字典数据等公共接口（如果有的话）
                        "/dict/**",
                        "/hospitals/**",
                        "/departments/**"
                        // 注意：以下接口需要登录验证，不在排除列表中：
                        // - /companions/*/favorite (收藏功能)
                        // - /messages/** (消息功能)
                        // - /user/** (用户个人信息相关)
                        // - /order/** (订单相关)
                        // - /companion/workbench/** (陪诊师工作台)
                        // - /patient/** (就诊人管理)
                        // - /ai/** (AI接口，允许未登录访问但会尝试解析Token)
                );
    }
}
