package com.minhagrana.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/minha-grana")
public class Controller {

    @GetMapping
    public String hello() {
        return "Hello, I'm minha grana.";
    }
}
