package com.fengx.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 路由限流策略
 *
 * @author fengXiong
 * @date 2019/12/5 10:30 上午
 */

@Configuration
public class RateLimiterConfiguration {

    /**
     * ip限流
     *
     * @return
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostName());
    }

}
