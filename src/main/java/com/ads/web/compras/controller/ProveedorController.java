package com.ads.web.compras.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ads.web.compras.entities.Proveedor;
import com.ads.web.compras.repository.ProveedorRepository;

@Controller

public class ProveedorController {

    @Autowired
    private ProveedorRepository proveedorRepository;


    //Proveedor
    @GetMapping("/compras/proveedor")
    public String obtenerProveedores(Model modelo) {
        List<Proveedor> listaProveedor = proveedorRepository.findAll();
        modelo.addAttribute("listaProveedor", listaProveedor);
        return "compras/proveedor";
    }
    @GetMapping("/compras/registrarProveedor")
    public String VistaRegistrarProveedor(Model modelo) {
        modelo.addAttribute("proveedor",new Proveedor());
        return "compras/proveedor_formulario";
    }
    @PostMapping("/compras/guardarProveedor")
    public String guardarProveedor(Proveedor proveedor) {
        proveedorRepository.save(proveedor);
        return "redirect:/compras/proveedor";
    }

    @GetMapping("/compras/proveedor/editar/{id}")
	public String mostrarFormularioDeModificarProveedor(@PathVariable Long  id,Model modelo) {
		Proveedor proveedor = proveedorRepository.findById(id).get();
		modelo.addAttribute("proveedor", proveedor);
	
		return "compras/proovedor_formulario";
	}
    @GetMapping("/compras/proveedor/eliminar/{id}")
	public String eliminarProveedor(@PathVariable Long id,Model modelo) {
		proveedorRepository.deleteById(id);
		return "redirect:/compras/proveedor";
	}


}

