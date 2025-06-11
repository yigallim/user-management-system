package com.xtemp.user_management.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PagedUserVO {
    Integer count;
    List<UserVO> rows;
}
