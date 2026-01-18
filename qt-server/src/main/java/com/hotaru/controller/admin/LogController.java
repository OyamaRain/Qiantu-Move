package com.hotaru.controller.admin;

import com.hotaru.dto.LogPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.result.Result;
import com.hotaru.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/log")
@Slf4j
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/page")
    @Tag(name = "日志管理")
    @Operation(summary = "分页查询")
    public Result<PageResult> page(@ParameterObject LogPageQueryDTO logPageQueryDTO){
        log.info("日志分页查询,参数:{}",logPageQueryDTO);
        PageResult pageResult = logService.pageQuery(logPageQueryDTO);
        return Result.success(pageResult);
    }
}
