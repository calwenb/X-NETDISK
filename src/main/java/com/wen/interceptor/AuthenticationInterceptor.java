package com.wen.interceptor;

import com.wen.annotation.PassToken;
import com.wen.servcie.TokenService;
import com.wen.servcie.UserService;
import com.wen.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * token验证拦截器类
 * 用于后端接口的验证，
 * 调用后端接口需携带token进行身份验证，
 * 使用@PassToken注解则跳过拦截器
 *
 * @author Mr.文
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;
    @Resource
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse response, Object object) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        // 从 http 请求参数中取出 token
        String token = httpServletRequest.getParameter("token");

        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有PassToken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        if (token == null) {
            response.getWriter().println(ResponseUtil.powerError("401"));
            return false;
        }
        // 执行认证,redis中若有此token，则放行
        if (tokenService.verifyToken(token)) {
            return true;
        }

        response.getWriter().println(ResponseUtil.powerError("401"));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

    }
}