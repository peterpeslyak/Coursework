package com.peslayk.controller;

import com.peslayk.model.User;
import com.peslayk.repository.UserRepository;
import com.peslayk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

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
        return "user/profile";
    }

    @GetMapping(value = "/changePassword")
    public String loadChangePassword(){
        return "user/change_password";
    }

    @PostMapping(value = "/user/profile/updatePassword")
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

    @PostMapping(value = "user/profile/editProfile/{idUser}")
    public String updateUserProfile(@PathVariable Long idUser,
                                    @ModelAttribute("user") User user,
                                    Model model, HttpSession session) {
        Optional<User> userWithEmail = userService.getUserByEmail(user.getEmail());
        if (userWithEmail.isPresent()) {
            User foundUser = userWithEmail.get();
            if (!foundUser.getIdUser().equals(idUser)) {
                System.out.println("Email " + user.getEmail() + " is already used by another user");
                session.setAttribute("msgEmail", "Email is already used by another user");
                return "redirect:/user/profile";
            }
        }
        User oldUser = userService.getUserById(idUser);
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPhoneNumber(user.getPhoneNumber());

        if (userService.editUserProfile(oldUser) != null) {
            System.out.println("Profile updated!");
            session.setAttribute("msg", "Profile updated!");
        } else {
            System.out.println("Something went wrong...");
            session.setAttribute("msg", "Something went wrong... Try again later.");
        }

        return "redirect:/user/profile";
    }

    @GetMapping(value = "/deleteAccount")
    public String deleteUserPage(){
        return "user/deleteAccount";
    }

    @PostMapping("deleteAccount/deleteUser")
    public String deleteUser(@RequestParam ("idUser") Long idUser, HttpSession session) {
        String message = userService.deleteUserById(idUser);
        session.setAttribute("msg", message);
        session.invalidate();
        System.out.println("User Account " + idUser + " is DELETED!");
        return  "redirect:/";
    }



}
