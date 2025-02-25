package com.ads.web.almacen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ads.web.almacen.entities.Categoria;
import com.ads.web.almacen.entities.MovimientoInventario;
import com.ads.web.almacen.entities.Producto;
import com.ads.web.almacen.repository.CategoriaRepository;
import com.ads.web.almacen.repository.ProductoRepository;
import com.ads.web.almacen.service.AlmacenService;

@Controller

public class AlmacenController {

    @Autowired
    private AlmacenService almacenService;

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    // Registrar ingreso de productos
    @PostMapping("/ingreso")
    public ResponseEntity<String> registrarIngreso(@RequestParam Long productoId, @RequestParam int cantidad) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        almacenService.registrarIngreso(producto, cantidad);
        return ResponseEntity.ok("Ingreso registrado correctamente");
    }

    // Registrar salida de productos
    @PostMapping("/salida")
    public ResponseEntity<String> registrarSalida(@RequestParam Long productoId, @RequestParam int cantidad) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        almacenService.registrarSalida(producto, cantidad);
        return ResponseEntity.ok("Salida registrada correctamente");
    }

    

    // Obtener el historial de movimientos
    @GetMapping("/historial")
    public ResponseEntity<List<MovimientoInventario>> obtenerHistorial() {
        return ResponseEntity.ok(almacenService.obtenerHistorial());
    }

    //Productos
    @GetMapping("/almacen/producto/inventario")
    public String obtenerInventario(Model modelo) {
        List<Producto> listaProductos = almacenService.obtenerInventario();
        modelo.addAttribute("listaProductos", listaProductos);
        return "productos";
    }
    @GetMapping("/almacen/producto/registrarProducto")
    public String VistaRegistrarProducto(Model modelo) {
        List<Categoria> listaCategorias = categoriaRepository.findAll();
        modelo.addAttribute("producto",new Producto());
		modelo.addAttribute("listaCategorias", listaCategorias);
        return "producto_formulario";
    }
    @PostMapping("/almacen/producto/guardarProducto")
    public String guardarProducto(Producto producto) {
        productoRepository.save(producto);
        return "redirect:/almacen/inventario";
    }

    @GetMapping("/almacen/producto/editar/{id}")
	public String mostrarFormularioDeModificarProducto(@PathVariable("id") Long  id,Model modelo) {
		Producto producto = productoRepository.findById(id).get();
		modelo.addAttribute("producto", producto);
		
        List<Categoria> listaCategorias = categoriaRepository.findAll();
		modelo.addAttribute("listaCategorias", listaCategorias);
		
		return "producto_formulario";
	}
    @GetMapping("/almacen/producto/eliminar/{id}")
	public String eliminarProducto(@PathVariable("id") Long id,Model modelo) {
		productoRepository.deleteById(id);
		return "redirect:/almacen/inventario";
	}
    //Categoria


}

