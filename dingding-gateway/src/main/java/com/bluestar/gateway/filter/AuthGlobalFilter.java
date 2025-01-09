package com.bluestar.gateway.filter;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("进入网关鉴权：拦截器");
        log.info("是否登录：" + StpUtil.isLogin());


        ////获得请求
        //ServerHttpRequest request = exchange.getRequest();
        ////获得token
        //List<HttpCookie> token = request.getCookies().get();
        //List<String> strings = request.getHeaders().get();
        ////解析token
        //if (token != null || strings != null) {
        //}
        ////放行

        return null;
    }

    //过滤器优先级
    @Override
    public int getOrder() {
        return 0;
    }
}
