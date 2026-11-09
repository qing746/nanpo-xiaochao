package com.cdutetc.nanpo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
http://localhost:8080/hello
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "南坡小炒，本店开业！";
    }
}
