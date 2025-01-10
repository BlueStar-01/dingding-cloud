package com.bluestar.gateway.filter;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.hutool.core.text.AntPathMatcher;
import com.BlueStar.dingding.constant.JwtClaimsConstant;
import com.BlueStar.dingding.utils.JwtUtil;
import com.bluestar.gateway.config.AuthProperties;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final AuthProperties authProperties;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final SaTokenConfig saTokenConfig;

    private boolean isExclude(String antPath) {
        for (String pathPattern : authProperties.getExcludePaths()) {
            if (antPathMatcher.match(pathPattern, antPath)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("进入网关鉴权：拦截器");
        //判断是否需要拦截
        if (isExclude(exchange.getRequest().getPath().toString())) {
            //不拦截放行
            return chain.filter(exchange);
        }
        //获得请求
        ServerHttpRequest request = exchange.getRequest();
        //获得token
        List<String> token = request.getHeaders().get("token");
        //解析token
        Claims user = null;
        try {
            if (token != null && token.size() > 0) {
                String tokenStr = token.get(0);
                user = JwtUtil.parseJWT(saTokenConfig.getJwtSecretKey(), tokenStr);
            }
        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }
        //传递token
        if (user == null) {
            return chain.filter(exchange);
        }
        String userinfo = user.get(JwtClaimsConstant.USER_ID).toString();
        ServerWebExchange swe = exchange.mutate()
                .request(builder -> builder.header(saTokenConfig.getJwtSecretKey(), userinfo))
                .build();
        //成功后放行
        return chain.filter(swe);
    }

    //过滤器优先级
    @Override
    public int getOrder() {
        return 0;
    }
}
