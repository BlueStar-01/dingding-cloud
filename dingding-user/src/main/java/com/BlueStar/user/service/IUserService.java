package com.BlueStar.user.service;

import com.BlueStar.user.domain.dto.UserDto;
import com.BlueStar.user.domain.dto.UserLoginDTO;
import com.BlueStar.user.domain.po.User;
import com.BlueStar.user.domain.vo.UserLoginVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author author
 * @since 2024-12-08
 */
public interface IUserService extends IService<User> {

    UserLoginVO login(UserLoginDTO loginDTO);

    UserLoginVO register(UserLoginDTO loginDTO);

    User updateUser(UserDto dto);
}
