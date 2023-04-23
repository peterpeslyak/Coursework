package com.peslayk.controller;

import com.peslayk.repository.UserRepository;
import com.peslayk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/")
    public String showHomePage() {
        return "Hello! Welcome to the website";
    }
}
