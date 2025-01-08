package com.BlueStar.dingding.exception;

/**
 * 用户不存在异常
 */
public class AccountNotFoundException extends BaseException {
    public AccountNotFoundException() {
    }

    public AccountNotFoundException(String msg) {
        super(msg);
    }
}
