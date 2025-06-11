package com.xtemp.user_management.interceptor;

import com.xtemp.user_management.entity.pojo.User;
import com.xtemp.user_management.exception.ForbiddenException;
import com.xtemp.user_management.exception.UnauthorizedException;
import com.xtemp.user_management.mapper.UserMapper;
import com.xtemp.user_management.service.impl.JwtService;
import com.xtemp.user_management.utils.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper; // Import ObjectMapper
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus; // Import HttpStatus
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        String path = request.getRequestURI();
        System.out.println("Request path: " + path);

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        if (path.startsWith("/auth")) {
            return true;
        }

        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedException("Missing or invalid Authorization header");
            }

            String token = authHeader.substring(7); // Remove "Bearer "

            String username = jwtService.extractUsername(token);
            if (username == null) {
                throw new UnauthorizedException("Invalid or expired token");
            }

            User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
            if (user == null) {
                throw new UnauthorizedException("User not found");
            }

            if (Boolean.TRUE.equals(user.getDisabled())) {
                throw new ForbiddenException("Your account is disabled");
            }

            if (path.startsWith("/user") && !"admin".equalsIgnoreCase(user.getRole().getValue())) {
                throw new ForbiddenException("Only admins can access user management endpoints");
            }

            return true;
        } catch (UnauthorizedException ex) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            try {
                response.getWriter().write(objectMapper.writeValueAsString(
                        ApiResponse.error("unauthorized", ex.getMessage())));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        } catch (ForbiddenException ex) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            try {
                response.getWriter().write(objectMapper.writeValueAsString(
                        ApiResponse.error("forbidden", ex.getMessage())));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        } catch (Exception ex) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json");
            try {
                response.getWriter().write(objectMapper.writeValueAsString(
                        ApiResponse.error("internal_server_error", "An unexpected error occurred during authentication.")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}