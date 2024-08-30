package com.app.springsecurity.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(path = {"/", "hello"})
    public String hello(HttpServletRequest req) {
        System.out.println("Request: " + req);
        return "Hello, World! " + req.getSession().getId();
    }

    @GetMapping("about")
    public String about() {
        return "This is a simple Spring Boot application with Spring Security.";
    }
}
