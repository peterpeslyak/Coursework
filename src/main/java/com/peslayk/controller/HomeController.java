package com.peslayk.controller;

import com.peslayk.model.User;
import com.peslayk.repository.UserRepository;
import com.peslayk.service.UserService;
//import jakarta.servlet.http.HttpSession;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ModelAttribute
    private void userDetails(Model m, Principal p){
        if (p!=null) {
            String email = p.getName();
            User user = userRepo.findByEmail(email);
            m.addAttribute("user", user);
        }
    }

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
            session.setAttribute("msgEmail", "Email is already used");
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

    @GetMapping(value = "/forgotPassword")
    public String forgotPassword(){
        return "forgotPassword";
    }

    @GetMapping(value = "/resetPassword/{id}")
    public String resetPassword(@PathVariable Long id, Model m){
        System.out.println(id);
        m.addAttribute("id", id);
        return "resetPassword";
    }

    @PostMapping(value = "/forgotPasswordAction")
    public String forgotPasswordAction(@RequestParam String email, @RequestParam String phoneNumber, HttpSession session){
        User user = userRepo.findByEmailAndPhoneNumber(email, phoneNumber);

        if (user!=null){
            return "redirect:/resetPassword/" + user.getId();
        }else{
            session.setAttribute("msg", "Incorrect email or phone number");
            return "forgotPassword";
        }
    }

    @PostMapping(value = "/changePassword")
    public String changePassword(@RequestParam String psw, @RequestParam Long id, HttpSession session) {
        User user = userRepo.findById(id).get();
        String encryptPsw = passwordEncoder.encode(psw);
        user.setPassword(encryptPsw);

        User updateUser = userRepo.save(user);

        if (updateUser != null) {
            session.setAttribute("msg", "Password Changed");
            return "redirect:/forgotPassword";
        } else {
            session.setAttribute("msgErr", "Something went wrong... Try again later");
            return "/resetPassword";
        }
    }

}
