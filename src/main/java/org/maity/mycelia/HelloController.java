package org.maity.mycelia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "Employee Management Service is running ✅";
    }

    @GetMapping("/healthz")
    public String health() {
        return "ok";
    }
}
