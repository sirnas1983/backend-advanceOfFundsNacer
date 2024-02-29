package com.nacer.reportes.mapper.user;

import com.nacer.reportes.dto.UserDTO;
import com.nacer.reportes.dto.AuthenticationUserDto;
import com.nacer.reportes.mapper.auditor.AuditorMapper;
import com.nacer.reportes.model.Rol;
import com.nacer.reportes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    @Autowired
    private AuditorMapper auditorMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getEmail()); // Assuming email is the username
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles().stream().map(Enum::name).collect(Collectors.toList()));
        dto.setAuditorDTO(auditorMapper.mapToAuditorDTO(user.getAuditor()));
        dto.setValidated(user.isValidated());
        dto.setUnlocked(user.isUnlocked());
        dto.setLastLoginDate(user.getLastLoginDate());
        return dto;
    }

    public User toUserNoPassword(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setRoles(userDTO.getRoles().stream().map(Rol::valueOf).collect(Collectors.toList()));
        return user;
    }

    public User toUserWithPassword(AuthenticationUserDto userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);
        return user;
    }

    public List<UserDTO> toDTOList(List<User> users) {
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
