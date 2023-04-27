package com.peslayk.controller;

import com.peslayk.model.User;
import com.peslayk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    @ModelAttribute
    private void userDetails(Model m, Principal p){
        String email = p.getName();
        User user =userRepo.findByEmail(email);
        m.addAttribute("user", user);
        m.addAttribute("user_role", user.getRole());
    }

    @GetMapping(value = "/")
    public String home(){
        return "user/home";
    }

    @GetMapping(value = "/profile")
    public String profile(){
        return "profile";
    }

    @GetMapping(value = "/changePassword")
    public String loadChangePassword(){
        return "user/change_password";
    }

    @PostMapping(value = "/updatePassword")
    public String changePassword(Principal p, @RequestParam("oldPass") String oldPass,
                                 @RequestParam("newPass") String newPass, HttpSession session){
        String email = p.getName();
        User loginUser = userRepo.findByEmail(email);
        if(passwordEncode.matches(oldPass, loginUser.getPassword())){
            System.out.println("Old password is correct");

            loginUser.setPassword(passwordEncode.encode(newPass));
            User updatePasswordUser = userRepo.save(loginUser);
            if(updatePasswordUser!=null){
                session.setAttribute("msg","Password changed");
            }else {
                session.setAttribute("msg","Something went wrong... Try again later!");
            }
        }else {
            session.setAttribute("msg","Old password is incorrect");
            System.out.println("Old password is incorrect");
        }
        return "redirect:/user/changePassword";
    }
}
