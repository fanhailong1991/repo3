package com.example.config;

import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component //注入spring容器
public class MyGlobalFilter implements GlobalFilter, Ordered {

    /**
     * 拦截业务逻辑
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        String token = queryParams.getFirst("token");
        if (StringUtils.isBlank(token)) {
            //设置响应状态码,提示用户未授权
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete(); //请求拦截
        }
        return chain.filter(exchange); //请求放行
    }

    /**
     * 返回执行顺序,数字越小,执行顺序越靠前
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
