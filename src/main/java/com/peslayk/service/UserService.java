package com.peslayk.service;

import com.peslayk.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    public User createUser(User user);

    public boolean checkEmail(String email);

    public User getUserById(Long idUser);

    public User editUserProfile(User user);

    public List<User> getAllUsers();

    public User updateRole(Long idUser, String role);

    public String deleteUserById(Long idUser);

    public Optional<User> getUserByEmail(String email);
}