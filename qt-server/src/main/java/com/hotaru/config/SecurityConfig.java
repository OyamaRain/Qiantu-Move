package com.hotaru.config;

import com.hotaru.filter.JwtAuthenticationFilter;
import com.hotaru.handler.AccessDeniedHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
// 启用方法级别的安全配置
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用 CSRF（使用 JWT 不需要）
                .csrf(csrf -> csrf.disable())

                // 禁用 session（JWT 是无状态的）
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 配置请求授权
                .authorizeHttpRequests(auth -> auth
                        // 放行登录接口
                        .requestMatchers("/admin/employee/login").permitAll()

                        // 放行文件上传接口
                        .requestMatchers("/admin/common/upload").authenticated()

                        // 放行 Swagger 文档
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/doc.html", "/webjars/**").permitAll()
                        .requestMatchers("/swagger-resources/**").permitAll()

                        // 所有 /admin/** 路径需要认证
                        .requestMatchers("/admin/**").authenticated()

                        // 其他请求暂时放行（根据实际需求调整）
                        //TODO 后续管理端完成后再修改
                        .anyRequest().permitAll()
                )

                // 禁用默认的表单登录
                .formLogin(form -> form.disable())

                // 禁用 HTTP Basic 认证
                .httpBasic(basic -> basic.disable())

                // 配置认证失败时返回StatusCode
                .exceptionHandling(exception ->
                        exception
                                .authenticationEntryPoint(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                                .accessDeniedHandler(accessDeniedHandler)
                )

                // 添加 JWT 过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 提供一个空的 AuthenticationManager，阻止自动配置
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}