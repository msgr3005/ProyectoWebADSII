package com.ads.web.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ads.web.almacen.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto,Long>{

}
