package com.shine.approval.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
@EnableAspectJAutoProxy
public class LogAutoConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void init() {
        logger.info("init LogAutoConfiguration Start");
    }

    @Bean
    public LogMethodAspect logMethodAspect(Environment env) {
        return new LogMethodAspect(env);
    }
}
