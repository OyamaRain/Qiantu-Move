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

        String token = null;
        String secretKey = null;

        // 1. 【动态识别】根据路径判断是管理端还是移动端用户
        if (requestURI.startsWith("/admin")) {
            token = request.getHeader(jwtProperties.getAdminTokenName());
            secretKey = jwtProperties.getAdminSecretKey();
        } else if (requestURI.startsWith("/user")) {
            token = request.getHeader(jwtProperties.getUserTokenName());
            secretKey = jwtProperties.getUserSecretKey();
        } else if (requestURI.startsWith("/mover")) {
            token = request.getHeader(jwtProperties.getUserTokenName());
            secretKey = jwtProperties.getUserSecretKey();
        }

        // 2. 如果 token 不存在
        if (!StringUtils.hasText(token)) {
            // 打印未携带 token 的日志
            //log.warn("请求路径: {} 未携带token", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 3. 校验令牌
            log.info("JWT令牌校验: {}", token);
            Claims claims = JwtUtil.parseJWT(secretKey, token);
            String roleStr = claims.get(JwtClaimsConstant.ROLES).toString();

            Long id;
            String name;

            // 2. 分类提取 ID 和 Name
            if (requestURI.startsWith("/admin")) {
                id = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
                // 提取员工姓名
                name = claims.get(JwtClaimsConstant.EMP_NAME).toString();
            } else {
                id = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
                // 提取用户昵称
                name = claims.get(JwtClaimsConstant.NICKNAME).toString();
            }

            // 3. 填充 ThreadLocal 供日志记录使用
            BaseContext.setCurrentId(id);
            BaseContext.setCurrentName(name);

            // 4. 配置 Security 上下文
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleStr));
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(id, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("✅ 鉴权通过 - ID: {}, 姓名/昵称: {}, 角色: {}", id, name, roleStr);
            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            log.error("❌ 请求路径: {} 令牌验证失败", requestURI, ex);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } finally {
            // 清理线程变量
            BaseContext.clear();
        }


    }
}