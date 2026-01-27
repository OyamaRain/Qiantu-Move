package com.hotaru.controller.user;

import com.hotaru.constant.JwtClaimsConstant;
import com.hotaru.dto.user.UserLoginDTO;
import com.hotaru.entity.User;
import com.hotaru.properties.JwtProperties;
import com.hotaru.result.Result;
import com.hotaru.service.user.UserService;
import com.hotaru.utils.JwtUtil;
import com.hotaru.vo.user.UserLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/auth")
@Slf4j
public class UserAuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    @Tag(name = "用户登录")
    @Operation(summary = "用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);
        User user = userService.login(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        claims.put(JwtClaimsConstant.PHONE, user.getPhone());
        claims.put(JwtClaimsConstant.USERNAME, user.getNickname());
        claims.put(JwtClaimsConstant.ROLES, user.getRole());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .phone(user.getPhone())
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }
}
