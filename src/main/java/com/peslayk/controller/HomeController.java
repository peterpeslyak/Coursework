package com.peslayk.controller;

import com.peslayk.model.User;
import com.peslayk.service.UserService;
//import jakarta.servlet.http.HttpSession;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public String index()
    {
        return "index";
    }

    @GetMapping(value = "/signin")
    public String login()
    {
        return "login";
    }

    @GetMapping(value = "/register")
    public String register()
    {
        return "register";
    }

    @PostMapping(value = "/createUser")
    public String createUser(@ModelAttribute User user, HttpSession session){
        //System.out.println(user);

        if (userService.checkEmail(user.getEmail())){
            System.out.println("Email " + user.getEmail() + " is already used");
            session.setAttribute("msg", "Email is already used");
        } else {
            User user1 = userService.createUser(user);
            if (user1!=null){
                System.out.println("Register complete!");
                session.setAttribute("msg", "Successfully registered!");
            } else {
                System.out.println("Something went wrong...");
                session.setAttribute("msg", "Something went wrong... Try again later.");
            }
        }

        return "redirect:/register";
    }

}
