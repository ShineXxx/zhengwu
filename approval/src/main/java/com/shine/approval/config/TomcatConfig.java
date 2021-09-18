package com.shine.approval.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zhj
 * @Date: 2020/9/24 17:43
 * @Description: tomcat配置类
 */
@Configuration
public class TomcatConfig {

    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.setProtocol("org.apache.coyote.http11.Http11Nio2Protocol");
        //nio2 异步非阻塞
        return tomcat;
    }

}