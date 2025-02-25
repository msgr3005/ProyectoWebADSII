package com.ads.web.almacen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ads.web.almacen.entities.MovimientoInventario;
import com.ads.web.almacen.entities.Producto;
import com.ads.web.almacen.repository.MovimientoInventarioRepository;
import com.ads.web.almacen.repository.ProductoRepository;

@Service
public class AlmacenService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private MovimientoInventarioRepository movimientoInventarioRepository;

    // Registrar ingreso de productos al almacén
    public Producto registrarIngreso(Producto producto, int cantidad) {
        producto.setStock(producto.getStock() + cantidad);
        productoRepository.save(producto);

        // Registrar el movimiento en el historial
        MovimientoInventario movimiento = new MovimientoInventario(
            producto, cantidad, "Ingreso");
        movimientoInventarioRepository.save(movimiento);

        return producto;
    }

    // Registrar salida de productos del almacén
    public Producto registrarSalida(Producto producto, int cantidad) {
        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);

        // Registrar el movimiento en el historial
        MovimientoInventario movimiento = new MovimientoInventario(
            producto, -cantidad, "Salida"
        );
        movimientoInventarioRepository.save(movimiento);

        return producto;
    }

    // Obtener el estado actual del inventario
    public List<Producto> obtenerInventario() {
        return productoRepository.findAll();
    }

    // Obtener historial de movimientos
    public List<MovimientoInventario> obtenerHistorial() {
        return movimientoInventarioRepository.findAll();
    }
}

