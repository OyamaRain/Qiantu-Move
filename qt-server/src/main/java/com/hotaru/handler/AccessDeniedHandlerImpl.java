package com.hotaru.handler;

import com.alibaba.fastjson2.JSON;
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
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {


        log.warn(
                "权限不足，拒绝访问 | uri={} | method={} | msg={}",
                request.getRequestURI(),
                request.getMethod(),
                accessDeniedException.getMessage()
        );

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        Result<Object> result = Result.error("权限不足");
        response.getWriter().write(
                JSON.toJSONString(result)
        );
    }
}
