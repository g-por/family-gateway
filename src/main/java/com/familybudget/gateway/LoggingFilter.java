package com.familybudget.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Instant start = Instant.now();

        ServerHttpRequest req = exchange.getRequest();
        String path = req.getURI().getPath();

        HttpMethod httpMethod = req.getMethod();
        String method = (httpMethod != null) ? httpMethod.name() : "UNKNOWN";

        // Маскуємо Authorization
        String auth = req.getHeaders().getFirst("Authorization");
        if (auth != null && auth.length() > 10) {
            auth = auth.substring(0, 10) + "...(masked)";
        }

        log.info("➡IN  {} {}  ip={}  auth={}  q={}",
                method, path,
                req.getRemoteAddress(),
                auth,
                req.getURI().getQuery());

        return chain.filter(exchange).doFinally(signalType -> {
            ServerHttpResponse res = exchange.getResponse();
            String statusStr = (res.getStatusCode() != null)
                    ? res.getStatusCode().toString()
                    : (res.getRawStatusCode() != null ? res.getRawStatusCode().toString() : "UNKNOWN");

            long ms = java.time.Duration.between(start, Instant.now()).toMillis();
            log.info("⬅OUT {} {}  status={}  {} ms", method, path, statusStr, ms);
        });
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
