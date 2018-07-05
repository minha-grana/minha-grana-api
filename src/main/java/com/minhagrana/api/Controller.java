package com.minhagrana.api;

import com.google.common.collect.Maps;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/minha-grana")
public class Controller {

    @GetMapping
    public ResponseEntity hello() {
        HashMap<String, String> map = Maps.newHashMap();
        map.put("greetings", "Hello, I'm minha grana.");
        return ResponseEntity.ok(map);
    }
}
