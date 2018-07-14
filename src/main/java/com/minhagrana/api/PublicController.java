package com.minhagrana.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/something")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Mono<String> getSomething() {
        return Mono.just("Here is something");
    }

}
