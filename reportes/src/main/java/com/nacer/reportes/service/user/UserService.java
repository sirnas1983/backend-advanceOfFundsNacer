package com.nacer.reportes.service.user;

import com.nacer.reportes.model.User;

import java.util.List;
import java.util.Optional;


public interface UserService {

    void addUser(User user);
    List<User> getUsers();
    Optional<User> getUserByEmail(String email);
}
