package com.hotaru.mapper;

import com.hotaru.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
    User getUserInfo(Long userId);
}
