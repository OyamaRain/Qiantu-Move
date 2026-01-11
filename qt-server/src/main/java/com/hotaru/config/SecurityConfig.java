package com.hotaru.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/employee/login").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        //放行 Knife4j 页面及其静态资源
                        .requestMatchers("/doc.html", "/webjars/**").permitAll()
                        //放行 Swagger/OpenAPI 的 JSON 接口定义路径
                        .requestMatchers("/v3/api-docs/**", "/swagger-resources/**").permitAll()
                        .anyRequest().permitAll() // 让JWT拦截器来处理认证
                );
        return http.build();
    }
}