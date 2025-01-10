package com.BlueStar.user.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.BlueStar.dingding.constant.MessageConstant;
import com.BlueStar.dingding.context.BaseContext;
import com.BlueStar.dingding.domain.Result;
import com.BlueStar.user.domain.dto.UserDto;
import com.BlueStar.user.domain.dto.UserLoginDTO;
import com.BlueStar.user.domain.po.User;
import com.BlueStar.user.domain.vo.UserLoginVO;
import com.BlueStar.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final IUserService userService;

    /**
     * 返回用户的详细信息。
     *
     * @return
     */
    @GetMapping("/info")
    public Result<User> info() {
        log.info("用户信息查询：{}", BaseContext.getCurrentId());
        User user = userService.getById(BaseContext.getCurrentId());
        user.setPassword(null);
        return Result.success(user);
    }

    @RequestMapping(" ")
    public Result isLogin() {
        return Result.success("是否登录：" + StpUtil.isLogin());
    }

    /**
     * 修改用户的信息
     *
     * @param dto
     * @return
     */
    @PutMapping("/info")
    public Result<User> update(@RequestBody UserDto dto) {
        log.info("用户信息修改：{}", dto);
        User user = userService.updateUser(dto);
        if (user == null) {
            return Result.error(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        return Result.success(user);
    }

    /**
     * 登录请求
     *
     * @param loginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO loginDTO) {
        UserLoginVO userLoginVO = userService.login(loginDTO);
        log.info("登录成功{}", userLoginVO);
        return Result.success(userLoginVO);
    }

    /**
     * 注册
     *
     * @param loginDTO
     * @return
     */
    @PostMapping("/register")
    public Result<UserLoginVO> register(@RequestBody UserLoginDTO loginDTO) {
        UserLoginVO userLoginVO = userService.register(loginDTO);
        log.info("注册成功{}", userLoginVO);
        return Result.success(userLoginVO);
    }
}
