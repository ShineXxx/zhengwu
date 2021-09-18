package com.shine.approval.interceptor;

import com.shine.common.constant.Constants;
import com.shine.common.exceptions.BaseException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019-06-30 14:54
 * fileName：AccessInterceptor
 * Use：
 */
public class AccessInterceptor implements HandlerInterceptor {

    private final CommitOnceFilter commitOnceFilter;

    public AccessInterceptor(CommitOnceFilter commitOnceFilter) {
        this.commitOnceFilter = commitOnceFilter;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IllegalAccessException, UnsupportedEncodingException {
        String requestUrl = request.getRequestURI();
        if (Constants.WRITE_LIST.stream().noneMatch(requestUrl::matches)) {
            // do something
            long expire = commitOnceFilter.checkRequestOnceCommit(request);
            if (expire != 0) {
                throw new BaseException("403", "操作太频繁，请" + expire + "秒后再试！");
            }
        }
        return true;
    }
}
