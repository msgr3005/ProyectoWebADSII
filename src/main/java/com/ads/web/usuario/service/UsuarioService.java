package com.ads.web.usuario.service;

import org.springframework.stereotype.Service;
@Service
public class UsuarioService {

    /*@Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(String username, String password) {
        if (usuarioRepository.existsByUsername(username)) {
            throw new RuntimeException("El usuario ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));

        // Asignar rol por defecto
        Rol rolCliente = rolRepository.findByNombre("CLIENTE")
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado"));
        usuario.setRoles(Collections.singleton(rolCliente));

        return usuarioRepository.save(usuario);
    }*/
}

