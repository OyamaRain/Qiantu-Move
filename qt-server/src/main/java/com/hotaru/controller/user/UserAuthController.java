package com.hotaru.controller.user;

import com.hotaru.dto.user.UserLoginDTO;
import com.hotaru.dto.user.UserUpdateInfoDTO;
import com.hotaru.result.Result;
import com.hotaru.service.user.UserService;
import com.hotaru.vo.user.SwitchRoleVO;
import com.hotaru.vo.user.UserLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/auth")
@Slf4j
public class UserAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Tag(name = "用户登录")
    @Operation(summary = "用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);
        UserLoginVO userLoginVO = userService.wxLogin(userLoginDTO.getCode());
        return Result.success(userLoginVO);
    }

    @PutMapping("/update")
    @Tag(name = "用户登录")
    @Operation(summary = "更新用户信息")
    public Result update(@RequestBody UserUpdateInfoDTO userUpdateInfoDTO) {
        log.info("更新用户信息：{}", userUpdateInfoDTO);
        userService.update(userUpdateInfoDTO);
        return Result.success();
    }

    @PreAuthorize("hasRole('MOVER')")
    @PostMapping("/switch")
    @Tag(name = "用户登录")
    @Operation(summary = "切换用户身份")
    public Result<SwitchRoleVO> switchRole(){
        SwitchRoleVO vo = userService.switchRole();
        log.info("切换当前用户身份:{}",vo);
        return Result.success(vo);
    }

}
