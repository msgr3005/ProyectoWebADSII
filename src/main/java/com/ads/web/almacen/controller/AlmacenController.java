package com.ads.web.almacen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ads.web.almacen.entities.Categoria;
import com.ads.web.almacen.entities.MovimientoInventario;
import com.ads.web.almacen.entities.Producto;
import com.ads.web.almacen.repository.CategoriaRepository;
import com.ads.web.almacen.repository.ProductoRepository;
import com.ads.web.almacen.service.AlmacenService;
import com.ads.web.almacen.service.ImagenServicio;

@Controller
public class AlmacenController {

    @Autowired
    private AlmacenService almacenService;

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ImagenServicio imagenServicio;


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

    @GetMapping("/almacen")
    public String VistaGestorAlmacen() {
        return "gestion_almacen";
    }
    //Productos
    @GetMapping("/almacen/inventario")
    public String obtenerInventario(Model modelo) {
        List<Producto> listaProductos = almacenService.obtenerInventario();
        modelo.addAttribute("listaProductos", listaProductos);
        return "almacen/productos";
    }
    @GetMapping("/almacen/registrarProducto")
    public String VistaRegistrarProducto(Model modelo) {
        List<Categoria> listaCategorias = categoriaRepository.findAll();
        modelo.addAttribute("producto",new Producto());
		modelo.addAttribute("listaCategorias", listaCategorias);
        return "almacen/producto_formulario";
    }
    @PostMapping("/almacen/guardarProducto")
    public String guardarProducto(Producto producto, BindingResult bindingResult) {
       /*  productoRepository.save(producto);
        return "redirect:/almacen/inventario";
        */
        if(bindingResult.hasErrors() || producto.getImagen().isEmpty()) {
			if(producto.getImagen().isEmpty()) {
				bindingResult.rejectValue("imagen","MultipartNotEmpty");
			}
			
			List<Categoria> categorias = categoriaRepository.findAll();
			return "admin/nueva-pelicula";
		}
		String rutaImagen = imagenServicio.almacenarArchivo(producto.getImagen());
		producto.setRutaImagen(rutaImagen);
		
		productoRepository.save(producto);
		return "redirect:/almacen/inventario";
    }

    @GetMapping("/almacen/productos/editar/{id}")
	public String mostrarFormularioDeModificarProducto(@PathVariable Long  id,Model modelo) {
		Producto producto = productoRepository.findById(id).get();
        List<Categoria> listaCategorias = categoriaRepository.findAll();
        modelo.addAttribute("producto", producto);
		modelo.addAttribute("listaCategorias", listaCategorias);
		
		return "almacen/producto_formulario";
	}
    @GetMapping("/almacen/productos/eliminar/{id}")
	public String eliminarProducto(@PathVariable Long id,Model modelo) {
		productoRepository.deleteById(id);
		return "redirect:/almacen/inventario";
	}
    //Categoria
    @GetMapping("/almacen/categorias")
	public String listarCategorias(Model modelo) {
		List<Categoria> listaCategorias = categoriaRepository.findAll();
		modelo.addAttribute("listaCategorias", listaCategorias);
		return "almacen/categorias";
	}

	@GetMapping("/almacen/registrarCategoria")
	public String vistaRegistrarCategoria(Model modelo) {
		modelo.addAttribute("categoria", new Categoria());
		return "almacen/categoria_formulario";
	}
	
	@PostMapping("/almacen/guardarCategoria")
	public String guardarCategoria(Categoria categoria) {
		categoriaRepository.save(categoria);
		return "redirect:/almacen/categorias";
	}

}

