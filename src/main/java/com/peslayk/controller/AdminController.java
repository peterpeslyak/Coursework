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
import java.util.List;

@Controller
@RequestMapping("/admin/")
public class AdminController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    @GetMapping("/")
    public String dashboard(){
        return "admin/dashboard";
    }

    @ModelAttribute
    private void userDetails(Model m, Principal p){
        String email = p.getName();
        User user =userRepo.findByEmail(email);
        m.addAttribute("user", user);
        m.addAttribute("user_role", user.getRole());
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

    //user dashboard page//

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PostMapping("/admin/users/updateRole")
    public String updateRole(@RequestParam ("userId") Long idUser,
                             @RequestParam ("role") String role, HttpSession session) {
        User oldUser = userRepo.findById(idUser).get();
        boolean isAdmin = oldUser.getRole().equals("ROLE_ADMIN");
        System.out.println(oldUser.getRole() + "--change to--" + role);
        if(userService.updateRole(idUser, role)!=null){
            System.out.println("Role hanged!");
            System.out.println(oldUser.getRole());
            if (isAdmin) {
                session.invalidate();
                System.out.println("Admin is auto-logout");
                return "redirect:/signin";
            }
            session.setAttribute("msg", "Successfully changed role!");

        } else {
            System.out.println(oldUser.getRole());
            System.out.println("Something went wrong...");
            session.setAttribute("msg", "Something went wrong... Try again later.");
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/deleteUser")
    public String deleteUser(@RequestParam ("userId") Long idUser, HttpSession session) {
        String message = userService.deleteUserById(idUser);
        session.setAttribute("msg", message);
        return  "redirect:/admin/users";
    }

}
