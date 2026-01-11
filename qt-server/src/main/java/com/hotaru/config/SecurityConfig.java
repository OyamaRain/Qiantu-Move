package com.hotaru.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. 禁用 CSRF（跨站请求伪造防护）
                .csrf(csrf -> csrf.disable())
                // 2. 允许所有请求，不再检查登录状态
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                // 3. 禁用表单登录页面（就是你看到的那个 HTML 页面）
                .formLogin(form -> form.disable());

        return http.build();
    }
}
