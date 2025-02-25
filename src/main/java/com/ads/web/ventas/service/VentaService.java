package com.ads.web.ventas.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ads.web.usuario.entities.User;
import com.ads.web.ventas.entities.Venta;
import com.ads.web.ventas.repository.VentaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;
    public Venta save(Venta venta) {
		return ventaRepository.save(venta);
	}

	public List<Venta> findAll() {
		return ventaRepository.findAll();
	}

    public List<Venta> findByUsername(User user) {
		return ventaRepository.findByUsuario(user);
	}

    public Optional<Venta> findById(Long id) {
        return ventaRepository.findById(id);
    }
}
