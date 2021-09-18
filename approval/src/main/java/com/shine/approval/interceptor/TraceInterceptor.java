package com.shine.approval.interceptor;

import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * traceId 拦截器
 *
 * @author zhaoyao
 */
public class TraceInterceptor extends HandlerInterceptorAdapter {
    public static final String TRACE_ID = "TRACE_ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // "traceId"
        MDC.put(TRACE_ID, UUID.randomUUID().toString());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
        MDC.clear();
    }
}