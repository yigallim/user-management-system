package com.xtemp.user_management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xtemp.user_management.entity.pojo.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenMapper extends BaseMapper<RefreshToken> {
}
