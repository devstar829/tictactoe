package com.telusko.simpleWebApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String greet() {
        System.out.println("im here");
        return "Welcome to Telusko";
    }

    @RequestMapping("/about")
    public String about() {
        return "We dont teach, we Educate";
    }
}
