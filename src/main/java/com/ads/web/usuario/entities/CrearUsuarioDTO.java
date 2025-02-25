package com.ads.web.usuario.entities;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearUsuarioDTO {
    private String username;
    private String password;
    private Set<String> roles;
}
