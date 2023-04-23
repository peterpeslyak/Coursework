package com.peslayk.service;

import com.peslayk.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User createUser(User user);

    public boolean checkEmail(String email);

}
