package com.hotaru.mapper;

import com.hotaru.entity.UserAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.security.Principal;

@Mapper
public interface AddressMapper {
    @Select("select * from user_address where id = #{id}")
    UserAddress getNameById(String id);
}
