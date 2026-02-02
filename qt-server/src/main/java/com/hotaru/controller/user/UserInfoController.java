package com.hotaru.controller.user;

import com.hotaru.context.BaseContext;
import com.hotaru.dto.user.CommentPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.result.Result;
import com.hotaru.service.user.UserInfoService;
import com.hotaru.vo.user.CommentDetailVO;
import com.hotaru.vo.user.CommentVO;
import com.hotaru.vo.user.UserInfoOrdersVO;
import com.hotaru.vo.user.UserInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user/userInfo")
@Slf4j
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/profile")
    @Tag(name = "个人中心")
    @Operation(summary = "获取小程序顶部个人信息")
    public Result<UserInfoVO> getUserInfo() {
        log.info("获取小程序顶部个人信息");
        Long currentId = BaseContext.getCurrentId();
        UserInfoVO userInfoVO = userInfoService.getUserInfo(currentId);
        return Result.success(userInfoVO);
    }

    @GetMapping("/orders")
    @Tag(name = "个人中心")
    @Operation(summary = "获取小程序顶部订单状态")
    public Result<UserInfoOrdersVO>  getUserInfoOrders() {
        log.info("获取小程序顶部订单状态");
        Long currentId = BaseContext.getCurrentId();
        UserInfoOrdersVO userInfoOrdersVO = userInfoService.getUserInfoOrders(currentId);
        return Result.success(userInfoOrdersVO);
    }

    @GetMapping("/comments")
    @Tag(name = "个人中心")
    @Operation(summary = "获取用户的评价")
    public Result<PageResult> getUserComments(@ParameterObject CommentPageQueryDTO commentPageQueryDTO) {
        log.info("获取用户的评价：{}", commentPageQueryDTO);
        Long currentId = BaseContext.getCurrentId();
        commentPageQueryDTO.setUserId(currentId);
        PageResult pageResult = userInfoService.getUserComments(commentPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/comments/{id}")
    @Tag(name = "个人中心")
    @Operation(summary = "获取用户的详细评价")
    public Result<CommentDetailVO> getComment(@PathVariable Long id) {
        log.info("获取用户的详细评价：{}", id);
        CommentDetailVO commentDetailVO = userInfoService.getCommentDetail(id);
        return Result.success(commentDetailVO);
    }

}
