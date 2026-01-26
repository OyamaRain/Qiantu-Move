package com.hotaru.service.user;

import com.hotaru.dto.user.UserLoginDTO;
import com.hotaru.entity.User;

public interface UserService {
    //用户登录
    User login(UserLoginDTO userLoginDTO);
}
