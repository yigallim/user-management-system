package com.xtemp.user_management.controller;

import com.xtemp.user_management.entity.dto.BaseAuthResponse;
import com.xtemp.user_management.entity.dto.UserCredentialsDTO;
import com.xtemp.user_management.entity.dto.WithRefreshToken;
import com.xtemp.user_management.exception.UnauthorizedException;
import com.xtemp.user_management.service.AuthService;
import com.xtemp.user_management.utils.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.xtemp.user_management.config.JwtProperties.REFRESH_TOKEN_DURATION;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<BaseAuthResponse>> register(@Valid @RequestBody UserCredentialsDTO dto, HttpServletResponse response) {
        WithRefreshToken withRefreshToken = authService.register(dto);

        response.addHeader("Set-Cookie",
                String.format("refreshToken=%s; HttpOnly; Path=/auth; Max-Age=%d; SameSite=Strict",
                        withRefreshToken.getRefreshToken(), REFRESH_TOKEN_DURATION));

        return ResponseEntity.ok(ApiResponse.success("Registration successful",
                new BaseAuthResponse(withRefreshToken.getAccessToken(), withRefreshToken.getUser())));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<BaseAuthResponse>> login(@Valid @RequestBody UserCredentialsDTO dto, HttpServletResponse response) {
        WithRefreshToken withRefreshToken = authService.login(dto);

        response.addHeader("Set-Cookie",
                String.format("refreshToken=%s; HttpOnly; Path=/auth; Max-Age=%d; SameSite=Strict",
                        withRefreshToken.getRefreshToken(), REFRESH_TOKEN_DURATION));

        return ResponseEntity.ok(ApiResponse.success("Login successful",
                new BaseAuthResponse(withRefreshToken.getAccessToken(), withRefreshToken.getUser())));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logout(
            @CookieValue(value = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        System.out.println(refreshToken);
        if (refreshToken != null && !refreshToken.isBlank()) {
            authService.logout(refreshToken);

            response.addHeader("Set-Cookie",
                    "refreshToken=; HttpOnly; Path=/auth; Max-Age=0; SameSite=Strict");
        }

        return ResponseEntity.ok(ApiResponse.success("Logged out successfully", null));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<BaseAuthResponse>> refreshToken(
            @CookieValue(value = "refreshToken", required = false) String refreshToken
    ) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new UnauthorizedException("Missing refresh token cookie");
        }
        WithRefreshToken withRefreshToken = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(ApiResponse.success("Token refreshed",
                new BaseAuthResponse(withRefreshToken.getAccessToken(), withRefreshToken.getUser())));
    }
}
