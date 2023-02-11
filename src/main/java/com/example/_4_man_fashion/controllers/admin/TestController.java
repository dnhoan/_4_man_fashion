package com.example._4_man_fashion.controllers.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
