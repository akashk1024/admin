package com.greatlearning.microservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "<h1> Welcome week10 Assignment - Admin Microservices</h1>";
    }

}
