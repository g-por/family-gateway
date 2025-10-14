package com.familybudget.gateway;

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyAccessLogConfig {
    @Bean
    public WebServerFactoryCustomizer<NettyReactiveWebServerFactory> nettyAccessLogCustomizer() {
        return factory -> factory.addServerCustomizers(httpServer -> httpServer.accessLog(true));
    }
}
