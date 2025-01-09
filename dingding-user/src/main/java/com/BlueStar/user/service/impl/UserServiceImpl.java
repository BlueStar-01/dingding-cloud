package com.BlueStar.user.service.impl;


import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.BlueStar.dingding.constant.JwtClaimsConstant;
import com.BlueStar.dingding.constant.MessageConstant;
import com.BlueStar.dingding.context.BaseContext;
import com.BlueStar.dingding.exception.AccountNotFoundException;
import com.BlueStar.dingding.exception.PasswordErrorException;
import com.BlueStar.dingding.utils.JwtUtil;
import com.BlueStar.user.domain.dto.UserDto;
import com.BlueStar.user.domain.dto.UserLoginDTO;
import com.BlueStar.user.domain.po.User;
import com.BlueStar.user.domain.vo.UserLoginVO;
import com.BlueStar.user.mapper.UserMapper;
import com.BlueStar.user.properties.JwtTokenProperty;
import com.BlueStar.user.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-12-08
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

    /**
     * 用户名密码登录
     *
     * @param loginDTO
     * @return
     */
    @Transactional
    @Override
    public UserLoginVO login(UserLoginDTO loginDTO) {
        //log.info("登录信息：{}",loginDTO);
        //获得数据
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        //先查询用户
        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda()
                .eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //验证密码
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!user.getPassword().equals(password)) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        //生成token
        return getUserLoginVO(user);
    }

    private UserLoginVO getUserLoginVO(User user) {
        StpUtil.login(user.getId(), SaLoginConfig
                .setExtra("name", user.getUsername()));
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        UserLoginVO loginVO = UserLoginVO.builder()
                .userId(user.getId())
                .token(tokenInfo.getTokenValue())
                .build();
        return loginVO;
    }

    /**
     * 注册
     *
     * @param loginDTO
     * @return
     */
    @Transactional
    @Override
    public UserLoginVO register(UserLoginDTO loginDTO) {
        log.info("注册信息：{}", loginDTO);
        //先查询用户
        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda()
                .eq(User::getUsername, loginDTO.getUsername());
        Long count = Long.valueOf(userMapper.selectCount(queryWrapper));
        if (count > 0) {
            throw new PasswordErrorException(MessageConstant.ACCOUNT_ALREADY_EXISTS);
        }

        //添加进数据库
        User user = User.builder()
                .username(loginDTO.getUsername())
                .password(DigestUtils.md5DigestAsHex(loginDTO.getPassword().getBytes()))
                .build();
        log.info("user:{}", user);
        userMapper.insert(user);

        //生成token
        return getUserLoginVO(user);
    }

    @Transactional
    @Override
    public User updateUser(UserDto dto) {
        //查询用户的信息
        User user = getById(BaseContext.getCurrentId());
        if (user == null) {
            return null;
        }
        //密码加密
        if (dto.getPassword() != null && !dto.getPassword().equals(user.getPassword())) {
            dto.setPassword(DigestUtils.md5DigestAsHex(dto.getPassword().getBytes()));
        }
        //复制属性
        BeanUtils.copyProperties(dto, user);
        user.setUpdateTime(LocalDateTime.now());
        //添加进入数据库
        log.info("修改后的user对象：{}", user);
        updateById(user);
        return user;
    }
}
