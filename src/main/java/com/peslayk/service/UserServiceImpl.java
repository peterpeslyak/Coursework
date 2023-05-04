package com.peslayk.service;

import com.peslayk.model.User;
import com.peslayk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private final UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User createUser(User user){

        user.setPassword(passwordEncode.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        return userRepo.save(user);
    }

    @Override
    public boolean checkEmail(String email){
        return userRepo.existsByEmail(email);
    }

    @Override
    public User getUserById(Long idUser) {
        return userRepo.findById(idUser).get();
    }

    @Override
    public User editUserProfile(User user) {
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User updateRole(Long idUser, String role) {
        User user = userRepo.findById(idUser).get();
        user.setRole(role);
        return userRepo.save(user);
    }

    @Override
    public String deleteUserById(Long idUser) {
        Optional<User> userOptional = userRepo.findById(idUser);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getRole().equals("ROLE_ADMIN")) {
                long countUsersWithRoleUser = userRepo.countByRole("ROLE_ADMIN");
                if (countUsersWithRoleUser == 1) {
                    return "You cannot delete last ADMIN account!";
                }
            }
            userRepo.delete(user);
            return "Account is deleted!";
        } else {
            return "Account not found!";
        }
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepo.findByEmail(email));
    }


}
