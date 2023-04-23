package com.peslayk.controller;

import com.peslayk.entity.User;
import com.peslayk.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @GetMapping("/users")
    public String findAll(){
        List<User> users = null;

        return "";
    }
}
