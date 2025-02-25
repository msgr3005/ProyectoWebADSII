package com.ads.web.usuario.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import com.ads.web.almacen.entities.Producto;
import com.ads.web.almacen.repository.ProductoRepository;
import com.ads.web.usuario.entities.User;
import com.ads.web.usuario.repository.UserRepository;
import com.ads.web.usuario.service.UserService;
import com.ads.web.ventas.entities.DetalleVenta;
import com.ads.web.ventas.entities.Venta;
import com.ads.web.ventas.repository.DetalleVentaRepository;
import com.ads.web.ventas.service.VentaService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	List<DetalleVenta> detalles = new ArrayList<DetalleVenta>();
	Venta venta = new Venta();
    @Autowired
    private ProductoRepository productoRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private VentaService ventaService;
	@Autowired
	private DetalleVentaRepository detalleVentaRepository;
   @GetMapping("")
	public String home(Model model, HttpSession session) {
		
		model.addAttribute("productos", productoRepository.findAll());
        
		//session
		model.addAttribute("sesion", session.getAttribute("idusuario"));

		return "usuario/home";
	}
    @PostMapping("/search")
	public String searchProduct(@RequestParam String nombre, Model model) {
		List<Producto> productos= productoRepository.findAll().stream().filter( p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
		model.addAttribute("productos", productos);		
		return "usuario/home";
	}
    @GetMapping("/admin")
	public String admin(Model model, HttpSession session) {
		//session
		model.addAttribute("sesion", session.getAttribute("idusuario"));

		return "admin";
	}
	@GetMapping("productohome/{id}")
	public String productoHome(@PathVariable Long id, Model model) {
		
		Optional<Producto> producto= productoRepository.findById(id);
		

		model.addAttribute("producto", producto.get());

		return "usuario/productohome";
	}
	@PostMapping("/cart")
	public String addCart(@RequestParam Long id, @RequestParam Integer cantidad, Model model) {
		DetalleVenta detalleVenta = new DetalleVenta();
		Producto producto = new Producto();
		double sumaTotal = 0;

		producto= productoRepository.findById(id).get();
		
		detalleVenta.setCantidad(cantidad);
		detalleVenta.setPrecio(producto.getPrecio());
		detalleVenta.setTotal(producto.getPrecio() * cantidad);
		detalleVenta.setProducto(producto);
		
		//validar que le producto no se añada 2 veces
		Long idProducto=producto.getId();
		boolean ingresado=detalles.stream().anyMatch(p -> p.getProducto().getId()==idProducto);
		
		if (!ingresado) {
			detalles.add(detalleVenta);
		}
		
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

		venta.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("venta", venta);

		return "usuario/carrito";
	}
	@GetMapping("/delete/cart/{id}")
	public String deleteProductoCart(@PathVariable Long id, Model model) {

		// lista nueva de prodcutos
		List<DetalleVenta> detalleVentaNuevo= new ArrayList<DetalleVenta>();

		for (DetalleVenta detalleVenta : detalles) {
			if (detalleVenta.getProducto().getId() != id) {
				detalleVentaNuevo.add(detalleVenta);
			}
		}

		// poner la nueva lista con los productos restantes
		detalles = detalleVentaNuevo;

		double sumaTotal = 0;
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

		venta.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("venta", venta);

		return "usuario/carrito";
	}
	@GetMapping("/venta")
	public String venta(Model model, HttpSession session) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User usuario = userRepository.findByUsername(username);
            
		model.addAttribute("cart", detalles);
		model.addAttribute("venta", venta);
		model.addAttribute("usuario", usuario);
		
		return "usuario/resumenorden";
	}
	@GetMapping("/guardarVenta")
	public String saveOrder(HttpSession session ) {
		LocalDateTime fechaCreacion = LocalDateTime.now();
		venta.setFecha(fechaCreacion);
		venta.setEstado("pendiente");
		venta.setUsuario(null);
		//usuario
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User usuario = userRepository.findByUsername(username);
      
		venta.setUsuario(usuario);
		ventaService.save(venta);
		
		//guardar detalles
		for (DetalleVenta dt:detalles) {
			dt.setVenta(venta);
			detalleVentaRepository.save(dt);
		}
		
		///limpiar lista y orden
		venta = new Venta();
		detalles.clear();
		
		return "redirect:/";
	}
}
