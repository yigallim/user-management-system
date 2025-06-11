package com.xtemp.user_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtemp.user_management.entity.pojo.RefreshToken;
import com.xtemp.user_management.mapper.RefreshTokenMapper;
import com.xtemp.user_management.service.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RefreshTokenServiceImpl extends ServiceImpl<RefreshTokenMapper, RefreshToken> implements RefreshTokenService {

    @Override
    public void revokeToken(String token) {
        RefreshToken rt = this.getOne(new QueryWrapper<RefreshToken>().eq("token", token));
        if (rt != null) {
            rt.setRevoked(true);
            this.updateById(rt);
        }
    }

    @Override
    public boolean isTokenValid(String token) {
        RefreshToken rt = this.getOne(new QueryWrapper<RefreshToken>()
                .eq("token", token)
                .eq("revoked", false));
        return rt != null && rt.getExpiresAt().isAfter(LocalDateTime.now());
    }
}
