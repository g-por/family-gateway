package com.familybudget.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @GetMapping("/api/gateway/ping")
    public String ping() {
        return "gateway";
    }
}
