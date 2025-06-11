package com.xtemp.user_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtemp.user_management.entity.dto.UserCredentialsDTO;
import com.xtemp.user_management.entity.pojo.User;
import com.xtemp.user_management.entity.vo.UserVO;
import com.xtemp.user_management.exception.ConflictException;
import com.xtemp.user_management.mapper.UserMapper;
import com.xtemp.user_management.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User registerUser(UserCredentialsDTO dto) {
        try {
            User user = new User();
            user.setDisabled(false);
            BeanUtils.copyProperties(dto, user);
            this.save(user);
            return user;
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Username already exists.");
        }
    }

    @Override
    public User loginUser(UserCredentialsDTO dto) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", dto.getUsername());
        User user = this.getOne(wrapper);

        if (user != null && user.getPassword().equals(dto.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public IPage<UserVO> getPagedUsers(int pageIndex, int pageSize) {
        Page<User> page = new Page<>(pageIndex, pageSize);
        IPage<User> userPage = this.page(page);
        log.info("getPagedUsers called with pageIndex: " + pageIndex + ", pageSize: " + pageSize);
        log.info("User page results (IPage<User>): " + userPage);
        log.info("Total records in userPage: " + userPage.getTotal());
        log.info("Records on current page (size of getRecords()): " + userPage.getRecords().size());

        IPage<UserVO> voPage = userPage.convert(user -> {
            UserVO vo = new UserVO();
            BeanUtils.copyProperties(user, vo);
            return vo;
        });
        return voPage;
    }
}
