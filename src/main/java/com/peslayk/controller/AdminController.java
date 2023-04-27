package com.peslayk.controller;

import com.peslayk.model.User;
import com.peslayk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/")
    public String home(){
        return "admin/home";
    }

    @ModelAttribute
    private void userDetails(Model m, Principal p){
        String email = p.getName();
        User user =userRepo.findByEmail(email);
        m.addAttribute("user", user);
    }
}
