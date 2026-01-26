package com.hotaru.mapper;

import com.hotaru.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where phone = #{phone}")
    User getByPhone(String phone);
}
