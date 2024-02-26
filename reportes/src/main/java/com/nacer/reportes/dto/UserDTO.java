package com.nacer.reportes.dto;

import com.nacer.reportes.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO  {

    private UUID id;
    private String nombre;
    private String email;
    private Set<Rol> roles = new HashSet<>();
    private AuditorDTO auditorDTO;

}
