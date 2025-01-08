package com.BlueStar.dingding.exception;

/**
 * 异常基类
 */
public class BaseException extends RuntimeException {
    BaseException() {

    }

    BaseException(String message) {
        super(message);
    }

}
