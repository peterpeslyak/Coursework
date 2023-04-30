package com.peslayk.service;

import com.peslayk.model.User;
import com.peslayk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

}
