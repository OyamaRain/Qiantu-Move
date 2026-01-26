package com.hotaru.service.user.impl;

import com.hotaru.constant.MessageConstant;
import com.hotaru.constant.StatusConstant;
import com.hotaru.dto.user.UserLoginDTO;
import com.hotaru.entity.User;
import com.hotaru.exception.AccountLockedException;
import com.hotaru.exception.AccountNotFoundException;
import com.hotaru.exception.PasswordErrorException;
import com.hotaru.mapper.UserMapper;
import com.hotaru.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String phone = userLoginDTO.getPhone();
        String password = userLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        User user = userMapper.getByPhone(phone);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //对前端的密码进行MD5加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (user.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return user;
    }
}
