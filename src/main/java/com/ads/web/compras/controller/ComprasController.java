package com.ads.web.compras.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ads.web.compras.entities.DetalleOrdenCompra;
import com.ads.web.compras.entities.OrdenCompra;
import com.ads.web.compras.entities.Proveedor;
import com.ads.web.compras.repository.DetalleOrdenCompraRepository;
import com.ads.web.compras.repository.OrdenCompraRepository;
import com.ads.web.compras.repository.ProveedorRepository;
import com.ads.web.usuario.entities.User;
import com.ads.web.usuario.repository.UserRepository;
import com.ads.web.ventas.entities.Venta;
@Controller
public class ComprasController {
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrdenCompraRepository ordenCompraRepository;
    @Autowired
    private DetalleOrdenCompraRepository detalleOrdenCompraRepository;
    //Proveedor
    @GetMapping("/compras")
    public String VistaGestorCompras(Model modelo) {
        return "gestion_compras";
    }

   @GetMapping("/compras/registrarCompra")
    public String mostrarFormulario(Model model) {
        List<Producto> productos = productoRepository.findAll();
        List<Proveedor> proveedores = proveedorRepository.findAll();
        model.addAttribute("productos", productos);
        model.addAttribute("proveedores", proveedores);
        return "compras/compras_formulario";
    }

    /*@PostMapping("/compras/guardarCompra")
    public String registrarCompra(@RequestParam Long proveedorId,@RequestParam Map<String, Integer> productos) {

        Proveedor proveedor = proveedorRepository.findById(proveedorId).orElse(null);
        if (proveedor == null) return "redirect:/compras?error";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User usuario = userRepository.findByUsername(username);

        OrdenCompra compra = new OrdenCompra();
        compra.setProveedor(proveedor);
        compra.setUsuario(usuario);
        compra.setFecha(LocalDateTime.now());

        List<DetalleOrdenCompra> detalles = new ArrayList<>();
        double totalCompra = 0;

        for (Map.Entry<String, Integer> entry : productos.entrySet()) {
            // Evitar procesar el proveedorId como si fuera un producto
            if (entry.getKey().equals("proveedorId")) {
                continue;
            }
    
            Long productoId = Long.parseLong(entry.getKey()); // Convertir clave String a Long
            Producto producto = productoRepository.findById(productoId).orElse(null);
            
            if (producto != null) {
                DetalleOrdenCompra detalle = new DetalleCompra();
                detalle.setProducto(producto);
                detalle.setCantidad(entry.getValue());
            }
        }
        return "redirect:/compras";
    }*/
    @PostMapping("/compras/guardarCompra")
    public String registrarCompra(@RequestParam Long proveedorId,@RequestParam Long productoId,@RequestParam Integer cantidad) {

        Optional<Proveedor> proveedor = proveedorRepository.findById(proveedorId);
        if (proveedor == null) return "redirect:/compras?error";
        Optional<Producto> producto = productoRepository.findById(productoId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User usuario = userRepository.findByUsername(username);

        OrdenCompra compra = new OrdenCompra();
        compra.setProveedor(proveedor.get());
        compra.setUsuario(usuario);
        compra.setFecha(LocalDateTime.now());
        compra.setEstado("pendiente");
        compra.setTotal(producto.get().getPrecio()*cantidad);
        DetalleOrdenCompra detalleOrdenCompra = new DetalleOrdenCompra();
        detalleOrdenCompra.setCantidad(cantidad);
        detalleOrdenCompra.setOrdenCompra(compra);
        detalleOrdenCompra.setPrecioUnitario(producto.get().getPrecio());
        detalleOrdenCompra.setProducto(producto.get());
        detalleOrdenCompra.setSubtotal(producto.get().getPrecio()*cantidad);

        ordenCompraRepository.save(compra);
        detalleOrdenCompraRepository.save(detalleOrdenCompra);
        /*List<DetalleOrdenCompra> detalles = new ArrayList<>();
        double totalCompra = 0;

        for (Map.Entry<String, Integer> entry : productos.entrySet()) {
            // Evitar procesar el proveedorId como si fuera un producto
            if (entry.getKey().equals("proveedorId")) {
                continue;
            }
    
            Long productoId = Long.parseLong(entry.getKey()); // Convertir clave String a Long
            Producto producto = productoRepository.findById(productoId).orElse(null);
            
            if (producto != null) {
                DetalleOrdenCompra detalle = new DetalleCompra();
                detalle.setProducto(producto);
                detalle.setCantidad(entry.getValue());
            }
        }*/
        return "redirect:/compras";
    }
    @GetMapping("/compras/listarCompras")
    public String listarCompras(Model model) {
        List<OrdenCompra> compras =ordenCompraRepository.findAll();
        model.addAttribute("listaCompras", compras);
        return "compras/compras";
    }

}
