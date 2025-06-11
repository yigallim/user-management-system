package com.xtemp.user_management.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xtemp.user_management.entity.dto.UserCredentialsDTO;
import com.xtemp.user_management.entity.pojo.User;
import com.xtemp.user_management.entity.vo.UserVO;

public interface UserService extends IService<User> {
    User registerUser(UserCredentialsDTO dto);

    User loginUser(UserCredentialsDTO dto);

    IPage<UserVO> getPagedUsers(int pageIndex, int pageSize);
}
