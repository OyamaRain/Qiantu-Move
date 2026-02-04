package com.hotaru.service.user;

import com.hotaru.dto.user.UserUpdateInfoDTO;
import com.hotaru.vo.user.SwitchRoleVO;
import com.hotaru.vo.user.UserLoginVO;

public interface UserService {

    // 微信登录
    UserLoginVO wxLogin(String code);

    // 更新用户信息
    void update(UserUpdateInfoDTO userUpdateInfoDTO);

    // 切换用户身份
    SwitchRoleVO switchRole();
}
