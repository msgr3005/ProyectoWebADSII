package com.ads.web.usuario.service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ads.web.usuario.entities.SecurityUser;
import com.ads.web.usuario.entities.User;

import com.ads.web.usuario.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor con ambos parámetros
   

    public String encodePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        }
        return passwordEncoder.encode(password);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new SecurityUser(user);
    }
    @Transactional
    public User registrarUsuario(String username, String password, String role) {
        User usuario = new User();
        usuario.setUsername(username);
     
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRole(role);
    
        System.out.println("Intentando guardar usuario: " + usuario.getUsername());
        
        User savedUser = userRepository.save(usuario);
        
        System.out.println("Usuario guardado con ID: " + savedUser.getId());
        
        return savedUser;
    }

}
