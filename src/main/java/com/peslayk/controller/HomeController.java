package com.peslayk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String index()
    {
        return "index";
    }

    @GetMapping(value = "/login")
    public String login()
    {
        return "login";
    }

    @GetMapping(value = "/register")
    public String register()
    {
        return "register";
    }
}
