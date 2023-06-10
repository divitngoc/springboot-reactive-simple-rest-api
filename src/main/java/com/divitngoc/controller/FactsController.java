package com.divitngoc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/facts")
public class FactsController {

    @GetMapping({"", "/"})
    public Mono<ResponseEntity<String>> getRandomFact() {
        return Mono.just(ResponseEntity.ok(""));
    }

}