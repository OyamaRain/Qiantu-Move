package com.hotaru.handler;

import com.alibaba.fastjson2.JSON;
import com.hotaru.context.BaseContext;
import com.hotaru.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // 从 ThreadLocal 获取当前是谁在尝试越权访问，方便事后审计
        Long currentId = BaseContext.getCurrentId();
        String currentName = BaseContext.getCurrentName();

        log.warn("⚠️ 权限不足 | 用户ID: {} | 姓名: {} | 路径: {} | 错误: {}",
                currentId, currentName, request.getRequestURI(), accessDeniedException.getMessage());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        // 返回更明确的错误信息
        Result<Object> result = Result.error("当前的账号权限不足，无法执行此操作");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
