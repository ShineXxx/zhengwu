package com.shine.approval.interceptor;

import com.shine.approval.service.RedisSetOperationService;
import com.shine.common.exceptions.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Title CommitOnceFilter
 * Description 防止重复提交过滤器
 *
 * @author berry_cooper
 * @version 1.0
 * @date 2019/8/13 12:21
 */
@Component
public class CommitOnceFilter {
    private final Logger logger = LoggerFactory.getLogger(CommitOnceFilter.class);

    private static final String REQUEST_ID = "requestId";

    private static final String GET = "GET";

    @Resource
    private RedisSetOperationService redisSetOperationService;

    long checkRequestOnceCommit(HttpServletRequest request) {
        if (request.getMethod().equals(GET)) {
            return 0;
        }
        String requestId = request.getHeader(REQUEST_ID);
        if (isBlank(requestId)) {
            logger.error("request head lost requestId field");
            throw new BaseException("403", "请求头缺少 requestId");
        }
        return redisSetOperationService.checkRequestIdExist(requestId);
    }
}
