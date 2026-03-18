package com.accompany.intercepter;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.accompany.exception.BaseException;
import com.accompany.properties.JwtProperties;
import com.accompany.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import com.accompany.util.StringUtils;
import com.accompany.base.BasicEnum;
import com.accompany.util.UserThreadLocal;
import java.util.Map;

@Component
public class UserIntercepter implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 判断当前请求是否是handler()
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        // 获取请求信息
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        
        // 特殊处理：允许GET方式访问陪诊师详情页面 /companions/{id}
        // 但不允许POST方式的收藏功能 /companions/{id}/favorite
        if ("GET".equals(method) && requestURI.matches("/companions/\\d+")) {
            return true;
        }

        // 获取token
        String token = request.getHeader("Token");
        if(StringUtils.isEmpty(token)){
            throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
        }
        
        // 解析token
        Map<String, Object> claims = JwtUtil.parseJWT(jwtProperties.getBase64EncodedSecretKey(), token);
        if(ObjectUtil.isEmpty(claims)){
            throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
        }
        
        Integer userId = MapUtil.get(claims, "userId", Integer.class);
        if(ObjectUtil.isEmpty(userId)){
            throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
        }

        // 把数据存储到线程中
        UserThreadLocal.setCurrentId(userId.longValue());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.removeCurrentId();
    }
}
