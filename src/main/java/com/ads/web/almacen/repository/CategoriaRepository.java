package com.ads.web.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ads.web.almacen.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria,Long>{

}