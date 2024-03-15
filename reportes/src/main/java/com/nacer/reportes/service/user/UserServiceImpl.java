package com.nacer.reportes.service.user;

import com.nacer.reportes.dto.UserDTO;
import com.nacer.reportes.dto.AuthenticationUserDto;
import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.mapper.user.UserMapper;
import com.nacer.reportes.model.Rol;
import com.nacer.reportes.model.TipoRegistro;
import com.nacer.reportes.model.User;
import com.nacer.reportes.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void addUser(AuthenticationUserDto userDto) {
        Objects.requireNonNull(userDto, "UserDto must not be null");

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("User with email " + userDto.getEmail() + " already exists");
        }

        User user = userMapper.toUserWithPassword(userDto);
        user.setRoles(Collections.singletonList(Rol.USER));
        userRepository.save(user);
        logger.info("User created successfully: {}", user.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> getUser(UUID id) {
        Objects.requireNonNull(id, "User ID must not be null");

        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDTOList(users);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        Objects.requireNonNull(email, "Email must not be null");

        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByEmail(String email) {
        Objects.requireNonNull(email, "Email must not be null");

        return userRepository.existsByEmail(email);
    }

    @Override
    public void editUser(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(userDTO.getId());
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("No se encontro usuario con ese ID");
        }
        User user = userOptional.get();
        user.setEmail(userDTO.getEmail());
        List<Rol> roles = new ArrayList<>();
        for (String rol : userDTO.getRoles()) {
            if(isValidRol(rol)){
                roles.add(Rol.valueOf(rol));
            } else {
                throw new ResourceNotFoundException("Rol " + rol + " no encontrado");
            }

        }
        user.setRoles(roles);
        user.setEnabled(userDTO.isValidated());
        user.setAccountNonLocked(userDTO.isUnlocked());
        user.setLastLoginDate(userDTO.getLastLoginDate());
        userRepository.save(user);
    }

    private boolean isValidRol(String rol) {
        try {
            Rol.valueOf(rol);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
