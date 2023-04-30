package com.peslayk.service;

import com.peslayk.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User createUser(User user);

    public boolean checkEmail(String email);

    public User getUserById(Long idUser);

    public User editUserProfile(User user);
}
