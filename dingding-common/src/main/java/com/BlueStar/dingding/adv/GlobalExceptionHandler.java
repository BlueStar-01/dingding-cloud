package com.BlueStar.dingding.adv;


import com.BlueStar.dingding.constant.MessageConstant;
import com.BlueStar.dingding.domain.Result;
import com.BlueStar.dingding.exception.BaseException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 捕捉业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result exception(BaseException e) {
        log.error("异常信息：{}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 处理重复添加两个用户
     */
    @ExceptionHandler
    public Result exception(SQLIntegrityConstraintViolationException e) {
        e.printStackTrace();
        String message = e.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] s = message.split(" ");
            //提醒账号已存在
            return Result.error(s[2] + MessageConstant.ACCOUNT_ALREADY_EXISTS);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }

    /**
     * 处理用户过期JWT问题
     *
     * @param jwtException
     * @return
     */
    @ExceptionHandler
    public Result exception(ExpiredJwtException jwtException) {
        log.info("JWT过期，{}", jwtException.getMessage());
        jwtException.printStackTrace();
        return Result.error(MessageConstant.ACCOUNT_EXPIRED_EXISTS);
    }


}
