package com.ads.web.reclamos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ads.web.reclamos.entities.Reclamo;
import com.ads.web.reclamos.repository.ReclamoRepository;
import com.ads.web.usuario.entities.User;
import com.ads.web.usuario.repository.UserRepository;
import com.ads.web.usuario.service.UserService;
import com.ads.web.ventas.entities.Venta;
import com.ads.web.ventas.service.VentaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/reclamos")

public class ReclamoController {
    @Autowired
    private ReclamoRepository reclamoRepository;
    @Autowired
    private VentaService ventaService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("reclamos/registrarReclamo")
    public String mostrarFormulario(Model modelo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User usuario = userRepository.findByUsername(username);

        List<Venta> ventas = ventaService.findByUsername(usuario);
        modelo.addAttribute("ventas", ventas);
        //modelo.addAttribute("reclamo",new Reclamo());
        return "/reclamos/reclamos_formulario";
    }

    @PostMapping("reclamos/guardarReclamo")
    public String guardarReclamo(@RequestParam String descripcion, @RequestParam Long ventaId) {
        Optional<Venta> venta = ventaService.findById(ventaId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User usuario = userRepository.findByUsername(username);
        Reclamo reclamo = new Reclamo();
        reclamo.setDescripcion(descripcion);
        reclamo.setFecha(LocalDateTime.now());
        reclamo.setEstado("pendiente");
        reclamo.setVenta(venta.get());
        reclamo.setUsuario(usuario);
        reclamoRepository.save(reclamo);
        return "redirect:/reclamos/listarReclamos";
    }

    @GetMapping("/reclamos/listarReclamos")
    public String listarReclamos(Model model) {
        List<Reclamo> reclamos = reclamoRepository.findAll();
        model.addAttribute("reclamos", reclamos);
        return "reclamos/listarReclamos";
    }
    @GetMapping("/reclamos")
    public String VistaGestorReclamos(Model modelo) {
        return "gestion_reclamos";
    }
}
