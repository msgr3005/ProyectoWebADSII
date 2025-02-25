package com.ads.web.usuario.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ads.web.usuario.entities.CrearUsuarioDTO;
import com.ads.web.usuario.entities.ERole;
import com.ads.web.usuario.entities.Rol;
import com.ads.web.usuario.entities.Usuario;
import com.ads.web.usuario.repository.UsuarioRepository;


@RestController
public class AuthController {

    @Autowired
    //private UsuarioService usuarioService;
    private UsuarioRepository repository;

    @GetMapping("/prueba")
    public String prueba() {
        return "prueba sin seguridad";
    }
    @GetMapping("/pruebasegura")
    public String pruebaSegura() {
        return "prueba con seguridad";
    }
    
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody CrearUsuarioDTO crearUsuarioDTO) {
        Set<Rol> roles = crearUsuarioDTO.getRoles().stream()
        .map(role->Rol.builder()
        .nombre(ERole.valueOf(role))
        .build()).collect(Collectors.toSet());
        Usuario usuario = Usuario.builder()
        .username(crearUsuarioDTO.getUsername())
        .password(crearUsuarioDTO.getPassword())
        .roles(roles).build();
        repository.save(usuario);
        /*usuarioService.registrarUsuario(usuario.getUsername(), usuario.getPassword());
        return ResponseEntity.ok("Usuario registrado con éxito");
    */return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/delete")
    public String deleteUsuario(@RequestParam String id){
        repository.deleteById(Long.parseLong(id));
        return "Se ha borrado";
    }
}
