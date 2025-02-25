package com.ads.web.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ads.web.almacen.entities.Producto;
import com.ads.web.almacen.repository.ProductoRepository;
import com.ads.web.usuario.entities.User;
import com.ads.web.usuario.repository.UserRepository;
import com.ads.web.ventas.entities.DetalleVenta;
import com.ads.web.ventas.entities.Venta;
import com.ads.web.ventas.repository.DetalleVentaRepository;
import com.ads.web.ventas.repository.VentaRepository;
import com.ads.web.ventas.service.VentaService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class VentaController {

    @Autowired
    private VentaService ventaService;
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    //ADMIN
    @GetMapping("/ventas")
    public String VistaGestorVentas() {
        return "gestion_ventas";
    }
    /*@GetMapping("ventas/cliente/{clienteId}")
    public String obtenerVentasPorCliente(@PathVariable("id") Long id,Model modelo) {
        List<Venta> listaVentas = ventaService.obtenerVentasPorCliente(id);
        modelo.addAttribute("listaVentas",listaVentas);
        return "/ventas/ventasPorCliente";
    }

    @GetMapping("ventas/{id}")
    public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Long id) {
        return ventaService.obtenerVentaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }*/
    //CLIENTE
    // Muestra el formulario de venta y envía la lista de productos disponibles
    @GetMapping("/ventas/registrarVenta")
    public String mostrarFormulario(Model model) {
        List<Producto> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
        return "ventas/ventas_formulario";
    }

    // Recibe los datos del formulario, crea y guarda la venta
    @PostMapping("ventas/guardarVenta")
    public String guardarVenta(@RequestParam Long productoId,
                               @RequestParam int cantidad) {
        Producto producto = productoRepository.findById(productoId).orElse(null);
        if(producto != null) {
            Venta venta = new Venta();
            DetalleVenta detalleVenta = new DetalleVenta();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User usuario = userRepository.findByUsername(username);
            
            //Venta
            venta.setUsuario(usuario);
            venta.setFecha(LocalDateTime.now());
            venta.setTotal(producto.getPrecio()*cantidad);// Se asume que Producto tiene getPrecio()
            venta.setEstado("Pendiente");
            ventaRepository.save(venta);

            //DetalleVenta
            detalleVenta.setCantidad(cantidad);
            detalleVenta.setPrecio(producto.getPrecio());
            detalleVenta.setProducto(producto);
            detalleVenta.setTotal(producto.getPrecio()*cantidad);
            detalleVenta.setVenta(venta);
            detalleVentaRepository.save(detalleVenta);
            
        }
        return "redirect:/ventas/listarVentas";
    }

    // Lista todas las ventas registradas
    @GetMapping("/ventas/listarVentas")
    public String listarVentas(Model model) {
        List<Venta> ventas = ventaRepository.findAll();
        model.addAttribute("ventas", ventas);
        return "ventas/ventas";
    }
    //Ver detalle de venta
    @GetMapping("/ventas/detalle/{id}")
	public String detalle(Model model, @PathVariable Long id) {
		Venta venta= ventaRepository.findById(id).get();
		
		model.addAttribute("detalles", venta.getDetalleVenta());
		
		return "ventas/detalleVenta";
	}
    
}