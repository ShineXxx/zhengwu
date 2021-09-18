package com.shine.approval.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
public class LogMethodAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int takeTimeThresholdValue = 100;

    private final Environment env;

    public LogMethodAspect(Environment env) {
        this.env = env;
    }

    /**
     * 默认在注解 @RestController有效
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restController() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * 环绕通知
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around(value = "restController()")
    public Object logMethodExecutionInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (logger.isDebugEnabled()) {
            logger.debug("请求方法: {}.{}()", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName());
            if (args != null) {
                List<Object> filter = Arrays.stream(args).filter(
                        arg -> !(arg instanceof ServletRequest)
                                && !(arg instanceof ServletResponse)
                                && !(arg instanceof MultipartFile)
                ).collect(Collectors.toList());
                String artStr = JSON.toJSONString(filter);
                logger.debug("请求参数: {}", artStr);
            }
        }
        try {
            long start = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - start;
            if (logger.isDebugEnabled()) {
                logger.debug("返回结果: {}", result);
            }
            if (executionTime > takeTimeThresholdValue) {
                logger.warn("time cost:[{}]ms", executionTime);
            }
            return result;
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument: {} in {}.{}()", Arrays.toString(args),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }

    }

    /**
     * 通知异常
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "execution(* com.shine.*.*(..))", throwing = "e")
    public void logThrow(JoinPoint joinPoint, Throwable e) {
        logger.error("Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);
    }

}

