package com.hotaru.mapper;

import com.hotaru.entity.User;
import com.hotaru.enumeration.RoleEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where open_id = #{openid}")
    User findByOpenid(String openid);

    void insert(User user);

    void update(User user);

    @Select("select * from user where id = #{userId}")
    User getById(Long userId);

    void updateRole(Long userId, RoleEnum role);
}
