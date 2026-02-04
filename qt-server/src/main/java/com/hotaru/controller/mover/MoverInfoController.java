package com.hotaru.controller.mover;

import com.hotaru.context.BaseContext;
import com.hotaru.dto.mover.CommentPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.result.Result;
import com.hotaru.service.mover.MoverInfoService;
import com.hotaru.vo.mover.MoverInfoOrdersVO;
import com.hotaru.vo.mover.MoverInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mover/moverInfo")
@Slf4j
public class MoverInfoController {

    @Autowired
    private MoverInfoService moverInfoService;

    @GetMapping("/orders")
    @Tag(name = "个人中心")
    @Operation(summary = "获取小程序顶部订单状态")
    public Result<MoverInfoOrdersVO> getMoverInfoOrders() {
        log.info("获取小程序顶部订单状态");
        Long currentId = BaseContext.getCurrentId();
        MoverInfoOrdersVO moverInfoOrdersVO = moverInfoService.getMoverInfoOrders(currentId);
        return Result.success(moverInfoOrdersVO);
    }

    @GetMapping("/profile")
    @Tag(name = "个人中心")
    @Operation(summary = "获取个人信息")
    public Result<MoverInfoVO> getMoverInfo() {
        log.info("获取个人信息");
        Long currentId = BaseContext.getCurrentId();
        MoverInfoVO moverInfoVO = moverInfoService.getMoverInfo(currentId);
        return Result.success(moverInfoVO);
    }

    @GetMapping("/comments")
    @Tag(name = "个人中心")
    @Operation(summary = "获取评价列表")
    public Result<PageResult> getComments(@ParameterObject CommentPageQueryDTO commentPageQueryDTO) {
        log.info("获取评价列表:{}", commentPageQueryDTO);
        PageResult pageResult = moverInfoService.getComments(commentPageQueryDTO);
        return Result.success(pageResult);
    }
}
