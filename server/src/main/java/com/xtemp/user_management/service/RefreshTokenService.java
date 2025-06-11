package com.xtemp.user_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xtemp.user_management.entity.pojo.RefreshToken;

public interface RefreshTokenService extends IService<RefreshToken> {
    void revokeToken(String token);
    boolean isTokenValid(String token);
}
