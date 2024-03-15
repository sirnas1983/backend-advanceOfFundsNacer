package com.nacer.reportes.service.user;

import com.nacer.reportes.dto.UserDTO;
import com.nacer.reportes.dto.AuthenticationUserDto;
import com.nacer.reportes.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserService {

    void addUser(AuthenticationUserDto user);
    Optional<UserDTO> getUser(UUID id);
    List<UserDTO> getUsers();
    Optional<User> getUserByEmail(String email);
    Boolean existsByEmail(String email);

    void editUser(UserDTO user);
}
