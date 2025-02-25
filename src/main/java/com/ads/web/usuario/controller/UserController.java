package com.ads.web.usuario.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ads.web.usuario.entities.User;
import com.ads.web.usuario.service.UserService;
import com.ads.web.ventas.service.VentaService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;



@Controller
@RequiredArgsConstructor
@RequestMapping("/usuario")

public class UserController {

	@Autowired
    private UserService userService;
    @Autowired
    private VentaService ventaService;
    @GetMapping
    public String getString(){
        return "get";
    }
    @GetMapping("/registrar")
    public String registrar() {
        return "usuario/registro";
    }
    
    /*@PostMapping("/registro")
    public ResponseEntity<User> registrarUsuario(@RequestBody User usuario) {
        System.out.println("Solicitud recibida en /registro con usuario: " + usuario.getUsername());
        User nuevoUsuario = userService.registrarUsuario(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getRole()
        );
        return ResponseEntity.ok(nuevoUsuario);
    }*/
    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String username,@RequestParam String password) {
       userService.registrarUsuario(
                username,
                password,
                "ROLE_ADMIN"
        );
        return "redirect:/";
    }
    @GetMapping("/login")
    public String login() {
        return "usuario/login";
    }
    @PostMapping("/acceder")
	public String acceder(User usuario, HttpSession session) {
		//logger.info("Accesos : {}", usuario);
		
		User user=userService.findByUsername(usuario.getUsername());
		//logger.info("Usuario de db: {}", user.get());
			session.setAttribute("idusuario", user.getId());
			
			if (user.getRole().equals("ROLE_ADMIN")) {
				return "redirect:/";
			}else {
				return "redirect:/";
			}
	}
    
    
}
