package com.BlueStar.dingding.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseContext {

    private static final Logger log = LoggerFactory.getLogger(BaseContext.class);
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        log.info("setCurrentId: {}", id);
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
