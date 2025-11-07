package com.familybudget.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PingControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    WebTestClient webTestClient;

    @Test
    void pingWorks() {
        webTestClient.get()
                .uri("http://localhost:" + port + "/api/gateway/ping")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("gateway");
    }
}
