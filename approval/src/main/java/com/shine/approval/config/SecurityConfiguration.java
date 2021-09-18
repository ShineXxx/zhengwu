//package com.shine.approval.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.filter.CorsFilter;
//
//import javax.annotation.Resource;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author xueancao
// */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//
//    @Resource
//    private CorsFilter corsFilter;
//
//    @Resource
//    Environment environment;
//
//    /**
//     * 1.OPTIONS 请求放行
//     * 2.静态资源放行
//     *
//     * @param web
//     * @throws Exception
//     */
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers(HttpMethod.OPTIONS, "/**")
//                .antMatchers("/i18n/**")
//                .antMatchers("/actuator/health")
//                .antMatchers("/static/**")
//                .antMatchers("/webjars/**");
//        String[] activeProfiles = environment.getActiveProfiles();
//        List<String> profiles = Arrays.asList(activeProfiles);
//        web.ignoring()
//                .antMatchers("/v2/api-docs")
//                .antMatchers("/swagger-resources/**")
//                .antMatchers("/swagger**");
//    }
//
//    /**
//     * 禁用默认csrf，自定义登录验证过滤器
//     * 防止将头信息添加到响应中
//     * 设置session创建策略-从不创建session
//     * 定义api访问权限
//     *
//     * @param http
//     * @throws Exception
//     */
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf()
//                .disable()
////                .cors().disable()
//                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling()
//                .and()
//                .headers()
//                .frameOptions().disable()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//
//                // 算法
//                .antMatchers("/api/algo/**").permitAll()
//                // 内部
//                .antMatchers("/api/inner/**").permitAll()
//                // 测试
//                .antMatchers("/api/testcase/**").permitAll()
//                // 登录
//                .antMatchers("/ajax/auth/login").permitAll()
//                // 结果前端
//                .antMatchers("/api/composite_win/front/**").permitAll()
//                // test
//                .antMatchers("/api/management/record/**").permitAll()
//
//                .antMatchers("/api/result/**").permitAll()
//
//                .antMatchers("/api/jiugong/**").permitAll()
//
//                .antMatchers("/api/file/**").permitAll()
//
//                .antMatchers("/api/management/**").permitAll()
//
//                .anyRequest().authenticated();
//
//    }
//}
