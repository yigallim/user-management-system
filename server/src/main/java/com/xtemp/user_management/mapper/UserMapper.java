package com.xtemp.user_management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xtemp.user_management.entity.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    
}
