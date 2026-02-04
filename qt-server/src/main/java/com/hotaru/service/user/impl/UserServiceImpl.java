package com.hotaru.service.user.impl;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hotaru.constant.JwtClaimsConstant;
import com.hotaru.constant.MessageConstant;
import com.hotaru.constant.StatusConstant;
import com.hotaru.context.BaseContext;
import com.hotaru.dto.user.UserUpdateInfoDTO;
import com.hotaru.entity.User;
import com.hotaru.enumeration.RoleEnum;
import com.hotaru.exception.RoleSwitchException;
import com.hotaru.exception.UserNotFoundException;
import com.hotaru.mapper.MoverMapper;
import com.hotaru.mapper.UserMapper;
import com.hotaru.properties.JwtProperties;
import com.hotaru.service.user.UserService;
import com.hotaru.utils.JwtUtil;
import com.hotaru.vo.user.SwitchRoleVO;
import com.hotaru.vo.user.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Value("${qt.wx.appId}")
    private String appId;

    @Value("${qt.wx.appSecret}")
    private String appSecret;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private MoverMapper moverMapper;

    @Override
    public UserLoginVO wxLogin(String code) {
        // 1. 拼接微信 API URL
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                appId, appSecret, code);

        // 2. 发送请求获取 openid 和 session_key
        String response = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = JSON.parseObject(response);

        String openid = jsonObject.getString("openid");
        if (openid == null) {
            throw new RuntimeException("微信登录失败: " + jsonObject.getString("errmsg"));
        }

        // 3. 数据库逻辑
        User user = userMapper.findByOpenid(openid);
        if (user == null) {
            user = new User();
            user.setOpenId(openid);
            user.setPhone("13800000000");
            user.setNickname("微信用户");
            user.setStatus(StatusConstant.ENABLE);
            user.setCreateTime(LocalDateTime.now());
            user.setRole(RoleEnum.USER);
            userMapper.insert(user);
        }

        // 4. 生成 JWT Token
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        claims.put(JwtClaimsConstant.ROLES, user.getRole());
        claims.put(JwtClaimsConstant.NICKNAME, user.getNickname());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setOpenid(openid);
        userLoginVO.setPhone(user.getPhone());
        userLoginVO.setToken(token);
        return userLoginVO;
    }

    @Override
    public void update(UserUpdateInfoDTO userUpdateInfoDTO) {
        Long currentId = BaseContext.getCurrentId();
        User user = new User();
        user.setId(currentId);
        user.setNickname(userUpdateInfoDTO.getNickname());
        user.setAvatar(userUpdateInfoDTO.getAvatar());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public SwitchRoleVO switchRole() {
        Long userId = BaseContext.getCurrentId();
        User currentUser = userMapper.getById(userId);

        if(currentUser == null){
            throw new UserNotFoundException(MessageConstant.USER_NOT_FOUND);
        }

        if(currentUser.getRole() == RoleEnum.USER){
            throw new RoleSwitchException("未申请成为搬家师傅");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, userId);
        claims.put(JwtClaimsConstant.ROLES, RoleEnum.MOVER);
        claims.put(JwtClaimsConstant.NICKNAME, currentUser.getNickname());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        return SwitchRoleVO.builder()
                .token(token)
                .build();

    }


}
