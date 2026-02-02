package com.hotaru.controller.user;

import com.hotaru.dto.user.ServiceItemPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.result.Result;
import com.hotaru.service.user.UserServiceItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/item")
@Slf4j
public class UserServiceItemController {

    @Autowired
    private UserServiceItemService userServiceItemService;


    @GetMapping("/list")
    @Tag(name="服务项功能接口")
    @Operation(summary = "查询服务项列表")
    public Result<PageResult> page(@ParameterObject ServiceItemPageQueryDTO serviceItemPageQueryDTO){
        log.info("用户查询服务项列表：{}",serviceItemPageQueryDTO);
        PageResult pageResult = userServiceItemService.pageQuery(serviceItemPageQueryDTO);
        return Result.success(pageResult);
    }
}
