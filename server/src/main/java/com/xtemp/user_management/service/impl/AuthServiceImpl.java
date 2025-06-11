package com.xtemp.user_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xtemp.user_management.entity.dto.UserCredentialsDTO;
import com.xtemp.user_management.entity.dto.WithRefreshToken;
import com.xtemp.user_management.entity.pojo.RefreshToken;
import com.xtemp.user_management.entity.pojo.User;
import com.xtemp.user_management.exception.UnauthorizedException;
import com.xtemp.user_management.service.AuthService;
import com.xtemp.user_management.service.RefreshTokenService;
import com.xtemp.user_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.xtemp.user_management.config.JwtProperties.ACCESS_TOKEN_DURATION;
import static com.xtemp.user_management.config.JwtProperties.REFRESH_TOKEN_DURATION;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public WithRefreshToken register(UserCredentialsDTO userCredentialsDTO) {
        User user = userService.registerUser(userCredentialsDTO);

        String accessToken = jwtService.generateToken(user, ACCESS_TOKEN_DURATION);
        String refreshToken = jwtService.generateToken(user, REFRESH_TOKEN_DURATION);

        refreshTokenService.save(RefreshToken.builder()
                .userId(user.getId())
                .token(refreshToken)
                .expiresAt(LocalDateTime.now().plus(REFRESH_TOKEN_DURATION, ChronoUnit.MILLIS))
                .revoked(false)
                .build());

        return new WithRefreshToken(accessToken, user.toUserDTO(), accessToken);
    }

    @Override
    public WithRefreshToken login(UserCredentialsDTO userCredentialsDTO) {
        User user = userService.loginUser(userCredentialsDTO);
        if (user == null) {
            throw new UnauthorizedException("Invalid username or password");
        }
        if (user.getDisabled()) {
            throw new UnauthorizedException("Disabled user account");
        }

        revokeOldTokens(user.getId());

        String accessToken = jwtService.generateToken(user, ACCESS_TOKEN_DURATION);
        String refreshToken = jwtService.generateToken(user, REFRESH_TOKEN_DURATION);

        refreshTokenService.save(RefreshToken.builder()
                .userId(user.getId())
                .token(refreshToken)
                .expiresAt(LocalDateTime.now().plus(REFRESH_TOKEN_DURATION, ChronoUnit.MILLIS))
                .revoked(false)
                .build());

        return new WithRefreshToken(accessToken, user.toUserDTO(), refreshToken);
    }

    @Override
    public WithRefreshToken refreshToken(String refreshToken) {
        boolean isValid = refreshTokenService.isTokenValid(refreshToken);
        if (!isValid) {
            throw new UnauthorizedException("Invalid or expired refresh token");
        }

        RefreshToken tokenRecord = refreshTokenService
                .getOne(new QueryWrapper<RefreshToken>().eq("token", refreshToken));

        User user = userService.getById(tokenRecord.getUserId());

        if (user == null || Boolean.TRUE.equals(user.getDisabled())) {
            throw new UnauthorizedException("Invalid user account");
        }

        String newAccessToken = jwtService.generateToken(user, ACCESS_TOKEN_DURATION);
        return new WithRefreshToken(newAccessToken, user.toUserDTO(), refreshToken);
    }

    private void revokeOldTokens(Integer userId) {
        QueryWrapper<RefreshToken> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("revoked", false);
        refreshTokenService.list(wrapper).forEach(token -> {
            token.setRevoked(true);
            refreshTokenService.updateById(token);
        });
    }

    @Override
    public void logout(String refreshToken) {
        RefreshToken token = refreshTokenService
                .getOne(new QueryWrapper<RefreshToken>()
                        .eq("token", refreshToken)
                        .eq("revoked", false));

        if (token != null) {
            token.setRevoked(true);
            refreshTokenService.updateById(token);
        }
    }
}
