package com.wen.config;

import com.wen.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/***
 * 新建Token拦截器
* @Title: InterceptorConfig.java
* @author MRC
* @date 2019年5月27日 下午5:33:28
* @version V1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
       /* registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");*/
    }
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        // 自己写的拦截器
        return new AuthenticationInterceptor();
    }//省略其他重写方法

}