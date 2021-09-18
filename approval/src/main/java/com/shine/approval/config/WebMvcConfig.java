package com.shine.approval.config;

import com.shine.approval.interceptor.TraceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2018-05-03 15:42
 * fileName：WebMvcConfig
 * Use：
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 注册拦截器
     * 防止重复提交拦截器， 限制 同一 requestId 频繁访问
     *
     * @param registry 拦截器实例
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/index.html", "/", "/ajax/auth/login", "/swagger-resources/**", "/swagger-ui.html");
    }
}