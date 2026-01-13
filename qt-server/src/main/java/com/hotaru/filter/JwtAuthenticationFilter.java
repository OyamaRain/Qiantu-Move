package com.hotaru.filter;

import com.hotaru.constant.JwtClaimsConstant;
import com.hotaru.context.BaseContext;
import com.hotaru.enumeration.RoleEnum;
import com.hotaru.properties.JwtProperties;
import com.hotaru.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 获取请求路径用于日志
        String requestURI = request.getRequestURI();

        // 1. 从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        // 2. 如果 token 不存在
        if (!StringUtils.hasText(token)) {
            // 打印未携带 token 的日志
            //log.warn("请求路径: {} 未携带token", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 3. 校验令牌
            log.info("JWT令牌校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
            // 校验员工角色
            String roleStr = claims.get(JwtClaimsConstant.ROLES).toString();
            RoleEnum role = RoleEnum.valueOf(roleStr);
            log.info("当前员工角色:{}", role);

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(
                    new SimpleGrantedAuthority("ROLE_" + role.name())
            );


            // 4. ThreadLocal 用于保存当前登录员工id（保持原有逻辑）
            BaseContext.setCurrentId(empId);
            log.info("当前员工id：{}", empId);

            // 5. 创建认证对象并设置到 Spring Security 上下文中
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(empId, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception ex) {
            log.error("请求路径: {} 令牌验证失败", requestURI, ex);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 7. 继续执行过滤器链
        filterChain.doFilter(request, response);
    }
}