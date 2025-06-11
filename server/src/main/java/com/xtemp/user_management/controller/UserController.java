package com.xtemp.user_management.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xtemp.user_management.entity.dto.UserCredentialsDTO;
import com.xtemp.user_management.entity.dto.UserUpdateDTO;
import com.xtemp.user_management.entity.pojo.User;
import com.xtemp.user_management.entity.vo.PagedUserVO;
import com.xtemp.user_management.entity.vo.UserVO;
import com.xtemp.user_management.exception.ResourceNotFoundException;
import com.xtemp.user_management.service.UserService;
import com.xtemp.user_management.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody UserCredentialsDTO dto) {
        User user = userService.registerUser(dto);
        return ResponseEntity
                .ok(ApiResponse.success("User created successfully.", user));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserVO>> getUser(@PathVariable Integer id) {
        User user = userService.getById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        return ResponseEntity.ok(ApiResponse.success("User found.", vo));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserVO>>> listUsers() {
        List<UserVO> users = userService.list().stream().map(user -> {
            UserVO vo = new UserVO();
            BeanUtils.copyProperties(user, vo);
            return vo;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Users listed.", users));
    }

    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<PagedUserVO>> getPagedUsers(
            @RequestParam(defaultValue = "1") Integer pageIndex,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        IPage<UserVO> pagedUsers = userService.getPagedUsers(pageIndex, pageSize);


        List<UserVO> userVOs = pagedUsers.getRecords().stream()
                .map(user -> {
                    UserVO vo = new UserVO();
                    BeanUtils.copyProperties(user, vo);
                    return vo;
                })
                .collect(Collectors.toList());

        PagedUserVO pagedUserVO = PagedUserVO.builder()
                .count((int) pagedUsers.getTotal())
                .rows(userVOs)
                .build();

        return ResponseEntity.ok(ApiResponse.success("Paged users retrieved successfully.", pagedUserVO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Integer id, @Valid @RequestBody UserUpdateDTO dto) {
        User user = userService.getById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        BeanUtils.copyProperties(dto, user);
        user.setUpdatedAt(null);
        userService.updateById(user);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully.", user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UserVO>> deleteUser(@PathVariable Integer id) {
        User user = userService.getById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userService.removeById(id);
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully.", vo));
    }

}